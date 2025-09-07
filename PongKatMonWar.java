package classes;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class PongKatMonWar implements GameStage {

    private boolean finished = false;
    private boolean banned = false;

    private final Scenario msg = new Scenario();
    private final Scanner sc = new Scanner(System.in);

    private static final int MAX_AVOID_COUNT = 3;

    private String playerInput = "";
    private String res = "";

    private int scenarioMode = 1;
    private int playerState = 1;
    private int enemyState = 1;
    private int avoidCount = MAX_AVOID_COUNT;
    private int enemyAvoidCount = MAX_AVOID_COUNT;

    @Override
    public void reset() {
        finished = false;
        banned = false;
        scenarioMode = 1;
        playerState = 1;
        enemyState = 1;
        avoidCount = MAX_AVOID_COUNT;
        enemyAvoidCount = MAX_AVOID_COUNT;
        playerInput = "";
        res = "";

        // 기존 리스트를 지우는 대신, 새로운 빈 리스트로 초기화합니다.
        // 이렇게 하면 NullPointerException을 방지하고 항상 안전한 상태로 시작할 수 있습니다.
        ToAccount.currentUser.playerPongKatMon = new ArrayList<>();
        PongKatMonRed.redPongKatMon = new ArrayList<>();
        PongKatMonRed.initializeList();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean isBanned() {
        return banned;
    }

    private boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private void selectMonster() {
        int choice = -1;

        while (true) {
            System.out.println("─ 어떤 퐁캣몬을 꺼내겠습니까? ─\n");
            showPlayerMonsters();
            String playerInput = sc.nextLine();

            if (!isNumber(playerInput)) {
                System.out.println("숫자를 입력해주세요.");
                continue;
            }

            choice = Integer.parseInt(playerInput) - 1;

            if (choice < 0 || choice >= ToAccount.currentUser.playerMonsters.size()) {
                System.out.println("범위 내 숫자를 입력해주세요.");
                continue;
            }
            break;
        }

        PongKatMon selected = ToAccount.currentUser.playerMonsters.get(choice);
        ToAccount.currentUser.playerPongKatMon.clear();
        ToAccount.currentUser.playerPongKatMon.add(selected);

        ToAccount.currentUser.playerMonsters.remove(choice);

        if (!PongKatMonRed.list.isEmpty()) {
            int redChoice = (int) (Math.random() * PongKatMonRed.list.size());
            PongKatMonRed.redPongKatMon.clear();
            PongKatMonRed.redPongKatMon.add(PongKatMonRed.list.get(redChoice));
            PongKatMonRed.list.remove(redChoice);
        }

        msg.initMonsters();

        scenarioMode = 2;
        playerState = 1;
        enemyState = 1;
    }

    private void healMonster(PongKatMon mon) {
        mon.hp += mon.max_hp * 0.3;
        if (mon.hp > mon.max_hp) mon.hp = mon.max_hp;
        mon.isAvoid = false;
    }

    private void healMonster(PongKatMonRed mon) {
        mon.hp += mon.max_hp * 0.3;
        if (mon.hp > mon.max_hp) mon.hp = mon.max_hp;
        mon.isAvoid = false;
    }

    private void selectSkill() {
        if (ToAccount.currentUser.playerPongKatMon.isEmpty() || PongKatMonRed.redPongKatMon.isEmpty()) {
            System.err.println("오류: 몬스터가 선택되지 않았습니다.");
            return;
        }

        PongKatMon playerMon = ToAccount.currentUser.playerPongKatMon.get(0);
        PongKatMonRed enemyMon = PongKatMonRed.redPongKatMon.get(0);

        System.out.println("─ 어떻게 하시겠습니까? ─\n");
        System.out.printf("① %s\t② 회복\t③ 피하기\n\n", playerMon.skillName);
        String playerInput = sc.nextLine();

        if (!isNumber(playerInput)) {
            System.out.println("목록에 있는 번호를 입력해주세요.");
            return;
        }

        int choice = Integer.parseInt(playerInput);
        if (choice < 1 || choice > 3) {
            System.out.println("잘못된 번호를 입력하셨습니다.");
            return;
        }

        switch (choice) {
            case 1 -> {
                if (!enemyMon.isAvoid) {
                    enemyMon.hp -= playerMon.attack;
                    playerState = Scenario.STATE_SECOND;
                } else {
                    playerState = Scenario.STATE_SIXTH;
                }
                playerMon.isAvoid = false;
            }
            case 2 -> {
                healMonster(playerMon);
                playerState = Scenario.STATE_THIRD;
            }
            case 3 -> {
                if (avoidCount > 0) {
                    playerMon.isAvoid = true;
                    avoidCount--;
                    playerState = Scenario.STATE_FOURTH;
                } else {
                    playerMon.isAvoid = false;
                    playerState = Scenario.STATE_FIFTH;
                }
            }
        }

        int enemyChoice = (int) (Math.random() * 3) + 1;
        switch (enemyChoice) {
            case 1 -> {
                if (!playerMon.isAvoid) {
                    playerMon.hp -= enemyMon.attack;
                    enemyState = Scenario.STATE_SECOND;
                } else {
                    enemyState = Scenario.STATE_FIFTH;
                }
                enemyMon.isAvoid = false;
            }
            case 2 -> {
                healMonster(enemyMon);
                enemyState = Scenario.STATE_THIRD;
            }
            case 3 -> {
                if (enemyAvoidCount > 0) {
                    enemyMon.isAvoid = true;
                    enemyAvoidCount--;
                    enemyState = Scenario.STATE_FOURTH;
                } else {
                    int fallbackChoice = (int) (Math.random() * 2) + 1;
                    if (fallbackChoice == 1) {
                        if (!playerMon.isAvoid) {
                            playerMon.hp -= enemyMon.attack;
                        }
                        enemyState = (playerMon.isAvoid) ? Scenario.STATE_FIFTH : Scenario.STATE_SECOND;
                    } else {
                        healMonster(enemyMon);
                        enemyState = Scenario.STATE_THIRD;
                    }
                    enemyMon.isAvoid = false;
                }
            }
        }
        checkBattleState();
    }

    private void checkBattleState() {
        if (ToAccount.currentUser.playerPongKatMon.isEmpty() || PongKatMonRed.redPongKatMon.isEmpty()) {
            return;
        }

        PongKatMon playerMon = ToAccount.currentUser.playerPongKatMon.get(0);
        PongKatMonRed enemyMon = PongKatMonRed.redPongKatMon.get(0);

        if (enemyMon.hp <= 0) {
            msg.defeatedName = enemyMon.name;
            // 승리한 플레이어 몬스터를 먼저 목록에 복귀
            if (playerMon.hp > 0) {
                ToAccount.currentUser.playerMonsters.add(playerMon);
            }

            if (!PongKatMonRed.list.isEmpty()) {
                int idx = (int) (Math.random() * PongKatMonRed.list.size());
                PongKatMonRed.redPongKatMon.clear();
                PongKatMonRed.redPongKatMon.add(PongKatMonRed.list.get(idx));
                PongKatMonRed.list.remove(idx);
                scenarioMode = Scenario.MODE_SELECT;
                playerState = Scenario.STATE_SECOND;
            } else {
                scenarioMode = Scenario.MODE_RESULT;
                playerState = Scenario.STATE_FIRST;

                msg.getPKWarScenario(scenarioMode, playerState, enemyState);

                System.out.println("소감 한 말씀 남겨주세요:");
                String res = sc.nextLine().trim();
                ToAccount.currentUser.level = 2;
                ToAccount.currentUser.money = 1000000;
                ToAccount.currentUser.victory = 100;
                ToAccount.currentUser.review = res;
                HallOfFame.list.add(new HallOfFame(ToAccount.currentUser.NickName, ToAccount.currentUser.ID,
                        ToAccount.currentUser.challenged, res));

                finished = true;
            }
        } else if (playerMon.hp <= 0) {
            msg.killedBy = enemyMon.skillName;

            if (!ToAccount.currentUser.playerMonsters.isEmpty()) {
                scenarioMode = Scenario.MODE_SELECT;
                playerState = Scenario.STATE_THIRD;
            } else {
                scenarioMode = Scenario.MODE_RESULT;
                playerState = Scenario.STATE_SECOND;

                msg.getPKWarScenario(scenarioMode, playerState, enemyState);

                ToAccount.currentUser.level = 1;
                ToAccount.currentUser.challenged++;
                banned = true;
            }
        }
    }

    private void showPlayerMonsters() {
        System.out.println("\n=== 플레이어 몬스터 목록 ===");
        List<PongKatMon> list = ToAccount.currentUser.playerMonsters;
        for (int i = 0; i < list.size(); i++) {
            PongKatMon mon = list.get(i);
            System.out.printf("%d. %s (스킬: %s, 공격: %d, 체력: %d)\n",
                    i + 1, mon.name, mon.skillName, mon.attack, mon.hp);
        }
    }

    private void showMonstersStats() {
        if (ToAccount.currentUser.playerPongKatMon.isEmpty() || PongKatMonRed.redPongKatMon.isEmpty()) {
            return;
        }

        PongKatMon playerMon = ToAccount.currentUser.playerPongKatMon.get(0);
        PongKatMonRed enemyMon = PongKatMonRed.redPongKatMon.get(0);

        System.out.printf("\n%s님의 %s\t공격력: %d 체력: %d\n",
                ToAccount.currentUser.NickName,
                playerMon.name,
                playerMon.attack,
                playerMon.hp);

        System.out.printf("레드의 %s\t\t공격력: %d 체력: %d\n",
                enemyMon.name,
                enemyMon.attack,
                enemyMon.hp);
    }

    @Override
    public void mPrint() {
        System.out.println("☆ 레드를 이겨라! ★\n");

        if (scenarioMode == 1) {
            msg.getPKWarScenario(scenarioMode, playerState, enemyState);
            selectMonster();
        } else if (scenarioMode == 2) {
            msg.getPKWarScenario(scenarioMode, playerState, enemyState);
            showMonstersStats();
            selectSkill();
        }
    }
}