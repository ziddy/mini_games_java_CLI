package classes;

import java.util.Scanner;

public class ArcheryGame implements GameStage {

	private final Scanner sc = new Scanner(System.in);

	private boolean finished = false;
	private boolean banned = false;

	private static final int MAX_CHANCE = 10;
	private static final int LEFT_POS = 1;
	private static final int CENTER_POS = 5;
	private static final int RIGHT_POS = 10;

	private int chance;
	private int hunted;
	private int moneyChange;
	private String narrator;
	private boolean isTurnStarted = false;

	public ArcheryGame() {
		reset();
	}

	@Override
	public void reset() {
		chance = MAX_CHANCE;
		hunted = 0;
		moneyChange = 0;
		narrator = "새를 맞춰보세요!";
		finished = false;
		banned = false;
		isTurnStarted = false;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public boolean isBanned() {
		return banned;
	}

	@Override
	public void mPrint() {
		char birdPosition = getRandomBirdPosition();

		// 1. 입력 받기 전 UI 출력 (제목, 내레이터, 메뉴 모두 보임)
		isTurnStarted = false;
		printUI(birdPosition, ' ');

		// 2. 입력 처리
		String input = sc.nextLine().trim();
		char prompt = input.length() > 0 ? input.charAt(0) : ' ';

		if (!isValidInput(prompt)) {
			narrator = "잘못된 입력입니다! 1, 2, 3 중에서 선택해주세요.";
			return;
		}

		// 3. 입력 후 결과 UI 출력 (제목, 내레이터, 메뉴 모두 숨김)
		isTurnStarted = true;
		printUI(birdPosition, prompt);

		// 4. 게임 로직 실행 (점수 계산)
		chance--;
		int remainingChance = chance;

		if (prompt == birdPosition) {
			hunted++;
			int reward = 1000 + (hunted * 500);
			ToAccount.currentUser.money += reward;
			moneyChange += reward;
			ToAccount.currentUser.victory++;
			narrator = String.format("새를 맞췄습니다! +%d원 [남은 기회 : %d번]\n", reward, remainingChance);
		} else {
			int penalty = 1000 + (hunted * 50);
			ToAccount.currentUser.money -= penalty;
			moneyChange -= penalty;
			narrator = String.format("아쉽네요! -%d원 [남은 기회 : %d번]\n", penalty, remainingChance);
		}

		// 6. 마지막 턴 종료 후 상태 업데이트
		if (chance <= 0) {
			printUI(birdPosition, 'A');
			checkGameOver();
		}
	}

	private boolean isValidInput(char input) {
		return input == '1' || input == '2' || input == '3';
	}

	private char getRandomBirdPosition() {
		return (char) ((int) (Math.random() * 3) + '1');
	}

	private void printUI(char birdPosition, char playerChoice) {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			}
		} catch (Exception e) {
		}

		int birdTap = getPosition(birdPosition);
		int promptTap = getPosition(playerChoice);

		if (chance == MAX_CHANCE) {
			System.out.println("\n☆ 양궁 게임 ★\n");
		}
		if (!isTurnStarted || chance <= 0) {
			System.out.printf("%s\n", narrator);
		}

		if (isTurnStarted && playerChoice != 'A') {
			System.out.printf("%s☜(º▽º)☞%s", repeatTab(birdTap), repeatLine(3));
		}
		System.out.printf("%s%s%s", repeatTab(promptTap),
				(isTurnStarted && (playerChoice == '1' || playerChoice == '2' || playerChoice == '3')) ? "     ↑" : "  ┌↑┐",
				repeatLine(2));

		// 게임 진행 중, 입력 대기 상태에서만 메뉴 출력
		if (!isTurnStarted) {
			System.out.println("① 왼쪽");
			System.out.println("② 가운데");
			System.out.println("③ 오른쪽");
			System.out.println("\n─ 어느 방향으로 쏘겠습니까? ─");
		}
	}

	private static String repeatLine(int count) {
		return "\n".repeat(Math.max(0, count));
	}

	private static String repeatTab(int count) {
		return "\t".repeat(Math.max(0, count));
	}

	private int getPosition(char position) {
		return switch (position) {
			case '1' -> LEFT_POS;
			case '2' -> CENTER_POS;
			case '3' -> RIGHT_POS;
			default -> CENTER_POS;
		};
	}

	private void checkGameOver() {
		if (hunted == 0) {
			narrator = "하나도 못 잡았군요? 탈락입니다.";
			System.out.println(narrator);
			ToAccount.currentUser.level = 1;
			ToAccount.currentUser.challenged++;
			banned = true;
		} else {
			narrator = String.format("수고하셨습니다 %s님!! 양궁 게임 클리어!\n", ToAccount.currentUser.NickName);
			System.out.printf(narrator);
			System.out.printf("\n★ 결과 ★\n승리 횟수 : %d회\n금액 : %d원\n",
					ToAccount.currentUser.victory,
					ToAccount.currentUser.money
			);
			waitForNextStage();
		}
		finished = true;
	}

	private void waitForNextStage() {
		while (true) {
			System.out.print("\n다음 단계로 넘어가고 싶으면 '다음'이라 입력해주세요: ");
			String res = sc.nextLine().trim();
			if (res.equals("다음")) {
				break;
			} else {
				System.out.println("입력이 올바르지 않습니다. 다시 입력해주세요.");
			}
		}
	}
}