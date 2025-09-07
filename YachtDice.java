package classes;

import java.util.Random;
import java.util.Scanner;

/*

 - 주사위를 최대 3번 굴려 점수를 계산하는 게임
 - 카테고리별 점수 계산 (Full House, Straight, Yacht 등)
 - 최고 점수에 따라 보상금 적립
 
 포트폴리오 포인트:
 1. 사용자 입력 기반 게임 진행 (CLI)
 2. 점수 계산 로직 구현 (패턴 분석)
 3. 객체지향적 구조 (인터페이스 GameStage 구현)

*/


public class YachtDice implements GameStage {

    private final Scanner sc = new Scanner(System.in);
    private final Random rand = new Random();

    private final int[] dice = new int[5];
    private final boolean[] hold = new boolean[5];

    private int reward; 
    private boolean finished = false;

    private final String[] categories = {
        "Four of a Kind", "Full House", "Small Straight", 
        "Large Straight", "Yacht", "Chance"
    };

    @Override
    public void reset() {
        for (int i = 0; i < dice.length; i++) {
            dice[i] = 0;
            hold[i] = false;
        }
        reward = 0;
        finished = false;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean isBanned() {
        return false;
    }

    //주사위 보유 상태 초기화 
    private void clearHold() {
        for (int i = 0; i < hold.length; i++) {
            hold[i] = false;
        }
    }

    //주사위 홀드(keep) 처리 
    private void holdDice(int rollCount) {
        clearHold();

        System.out.println("\n[Enter] 키로 진행, 숫자 입력 시 해당 주사위 유지 (예: 1 3 5)");

        String input = sc.nextLine().trim();

        if (input.equalsIgnoreCase("quit")) {
            System.out.println("게임을 종료합니다.");
            finished = true;
            return;
        }

        if (!input.isEmpty()) {
            for (String token : input.split(" ")) {
                try {
                    int idx = Integer.parseInt(token) - 1;
                    if (idx >= 0 && idx < 5) {
                        hold[idx] = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Error] 숫자만 입력 가능합니다.");
                }
            }
        }

        // 홀드하지 않은 주사위는 다시 굴릴 수 있도록 초기화
        for (int i = 0; i < dice.length; i++) {
            if (!hold[i]) dice[i] = 0;
        }
    }

    @Override
    public void mPrint() {
        System.out.println("\n=== Yacht Dice Game ===");
        System.out.println("\n[DICE] 주사위 굴리기 | [QUIT] 종료");
        System.out.print("▶ 입력: ");
        String command = sc.nextLine();

        switch (command.toLowerCase()) {
            case "dice" -> playDice(); // 실제 주사위 게임 실행
            case "quit" -> {
                System.out.println("게임을 종료합니다.");
                finished = true; // 탈락처럼 처리 가능
            }
            default -> {
                System.out.println("[Error] 올바른 명령어를 입력하세요.");
                finished = false; // stage 진행 금지
            }
        }
    }

    //주사위 게임 메인 로직 
    private void playDice() {
        for (int roll = 0; roll < 3; roll++) {
            if (roll > 0) {
                System.out.printf("Roll %d 결과: %s%n", roll, formatDice());
                holdDice(roll);
            } else {
                System.out.println("게임 시작! 주사위를 굴려주세요.");
                holdDice(roll);
            }

            // 주사위 굴리기 (홀드 안 된 주사위만)
            for (int i = 0; i < dice.length; i++) {
                if (dice[i] == 0) dice[i] = rand.nextInt(6) + 1;
            }
        }

        System.out.printf("최종 주사위: %s%n", formatDice());
        calculateReward();
        finished = true;
    }

    // 주사위 배열을 보기 좋게 문자열로 반환 
    private String formatDice() {
        StringBuilder sb = new StringBuilder();
        for (int d : dice) sb.append("[").append(d).append("]");
        return sb.toString();
    }

    // 점수 계산 및 보상 처리 
    private void calculateReward() {
        int maxScore = 0;
        System.out.println("=== 점수표 ===");
        for (int i = 0; i < categories.length; i++) {
            int score = getScore(dice, i);
            System.out.printf("%-15s : %d%n", categories[i], score);
            maxScore = Math.max(maxScore, score);
        }

        // 보상금 규칙
        if (maxScore <= 20) reward = 50 * maxScore;
        else if (maxScore <= 25) reward = 100 * maxScore;
        else if (maxScore <= 30) reward = 150 * maxScore;
        else if (maxScore <= 40) reward = 200 * maxScore;
        else reward = 400 * maxScore;

        System.out.printf("최고 점수: %d → 보상: %,d원%n", maxScore, reward);

        // 사용자 계좌 반영
        ToAccount.currentUser.money += reward;
    }

    //카테고리별 점수 계산 
    private int getScore(int[] dice, int category) {
        int[] counts = new int[7];
        for (int d : dice) counts[d]++;

        switch (category) {
            case 0: // Four of a Kind
                for (int i = 1; i <= 6; i++)
                    if (counts[i] >= 4) return sumDice(dice);
                return 0;

            case 1: // Full House
                boolean hasThree = false, hasTwo = false;
                for (int i = 1; i <= 6; i++) {
                    if (counts[i] == 3) hasThree = true;
                    if (counts[i] == 2) hasTwo = true;
                }
                return (hasThree && hasTwo) ? 25 : 0;

            case 2: // Small Straight
                return hasStraight(counts, 4) ? 30 : 0;

            case 3: // Large Straight
                return hasStraight(counts, 5) ? 40 : 0;

            case 4: // Yacht
                for (int i = 1; i <= 6; i++)
                    if (counts[i] == 5) return 50;
                return 0;

            case 5: // Chance
                return sumDice(dice);

            default:
                return 0;
        }
    }

    private int sumDice(int[] dice) {
        int total = 0;
        for (int d : dice) total += d;
        return total;
    }

    private boolean hasStraight(int[] counts, int length) {
        int consecutive = 0;
        for (int i = 1; i <= 6; i++) {
            if (counts[i] > 0) {
                consecutive++;
                if (consecutive >= length) return true;
            } else {
                consecutive = 0;
            }
        }
        return false;
    }
}

