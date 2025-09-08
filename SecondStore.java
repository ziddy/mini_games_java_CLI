package classes;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class SecondStore implements GameStage {

    private final Scanner sc = new Scanner(System.in);

    private boolean finished = false;
    private boolean banned = false;
    private String narrator = "여기까지 오느라 고생하셨습니다. 퐁캣몬을 골라주세요.";
    private String playerInput = "";

    private static final int MAX_MONSTERS = 6;

    @Override
    public void reset() {
        finished = false;
        banned = false;
        narrator = "여기까지 오느라 고생하셨습니다. 퐁캣몬을 골라주세요.";
        playerInput = "";
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean isBanned() {
        return banned;
    }

    /** 숫자인지 확인 */
    private boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /** 몬스터 구매 */
    private void buyMonster() {
        System.out.println("\n─ 구매할 퐁캣몬 번호 입력 후 '시작' 입력 ─\n");
        playerInput = sc.nextLine().trim();

        if (!isNumber(playerInput)) {
            handleNonNumberInput();
            return;
        }

        int choice = Integer.parseInt(playerInput) - 1;
        if (choice < 0 || choice >= PongKatMon.list.size()) {
            narrator = "잘못된 번호를 입력하셨습니다.";
            return;
        }

        PongKatMon original = PongKatMon.list.get(choice);

        if (!canBuyMonster(original)) {
            narrator = String.format("%s을(를) 살 수 없습니다. 포기하려면 '포기' 입력.", original.name);
            return;
        }

        if (ToAccount.currentUser.playerMonsters.size() >= MAX_MONSTERS) {
            narrator = "최대 6마리까지만 구매할 수 있습니다.";
            return;
        }

        addMonsterToPlayer(original);
        narrator = String.format("%s을(를) 구매했습니다!", original.name);
        ToAccount.currentUser.money -= original.needMoney;
    }

    /** 숫자가 아닌 입력 처리 */
    private void handleNonNumberInput() {
        if (playerInput.equals("시작")) {
            if (ToAccount.currentUser.playerMonsters.isEmpty()) {
                narrator = "퐁캣몬 없이 다음으로 넘어갈 수 없습니다. '포기'를 입력하세요.";
                return;
            }
            PongKatMonRed.initializeList();
            finished = true;
        } else if (playerInput.equals("포기")) {
            narrator = "탈락입니다.";
            ToAccount.currentUser.level = 1;
            ToAccount.currentUser.challenged++;
            banned = true;
        } else {
            narrator = "숫자를 입력해주세요.";
        }
    }

    /** 구매 가능 여부 확인 */
    private boolean canBuyMonster(PongKatMon mon) {
        return mon.needMoney <= ToAccount.currentUser.money && mon.needWin <= ToAccount.currentUser.victory;
    }

    /** 플레이어 리스트에 새로운 몬스터 추가 */
    private void addMonsterToPlayer(PongKatMon original) {
        PongKatMon newMon = new PongKatMon(
                original.name, original.skillName, original.attack, original.hp,
                original.max_hp, original.needMoney, original.needWin, original.isAvoid
        );
        ToAccount.currentUser.playerMonsters.add(newMon);
    }

    /** 플레이어 몬스터 목록 출력 */
    private void showPlayerMonsters() {
        System.out.println("=== 플레이어 몬스터 목록 ===");
        int idx = 1;
        for (PongKatMon mon : ToAccount.currentUser.playerMonsters) {
            System.out.printf("%d. %s (스킬: %s, 공격: %d, 체력: %d)\n",
                    idx++, mon.name, mon.skillName, mon.attack, mon.hp);
        }
    }

    /** 상점 화면 출력 */
    @Override
    public void mPrint() {
        System.out.println("\n☆ 퐁캣몬 상점 ★\n");
        System.out.printf("%s\n",narrator);
        System.out.printf("지갑: %d원, 승리 횟수: %d\n\n", ToAccount.currentUser.money, ToAccount.currentUser.victory);

        showPlayerMonsters();

        System.out.println();
        int idx = 1;
        for (PongKatMon mon : PongKatMon.list) {
            System.out.printf("No.%d ☆ %s ☆ (%d원 / 승리 %d회 이상)\n",
                    idx++, mon.name, mon.needMoney, mon.needWin);
        }

        buyMonster();
    }
}
