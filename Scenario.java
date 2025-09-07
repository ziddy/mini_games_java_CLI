package classes;

class Scenario {
    String defeatedName = "";
    String killedBy = "";

    // 모드 상수
    public static final int MODE_SELECT = 1;
    public static final int MODE_BATTLE = 2;
    public static final int MODE_RESULT = 4;

    // 상태 상수
    public static final int STATE_FIRST = 1;
    public static final int STATE_SECOND = 2;
    public static final int STATE_THIRD = 3;
    public static final int STATE_FOURTH = 4;
    public static final int STATE_FIFTH = 5;
    public static final int STATE_SIXTH = 6;

    // 필드 선언 (객체 참조)
    private PongKatMon playerMon;
    private PongKatMonRed redMon;

    // 초기화 메소드
    public void initMonsters() {
        if (ToAccount.currentUser.playerPongKatMon.isEmpty()) {
            return; // null 방지
        }
        if (PongKatMonRed.redPongKatMon.isEmpty()) {
            return;
        }

        playerMon = ToAccount.currentUser.playerPongKatMon.get(0);
        redMon = PongKatMonRed.redPongKatMon.get(0);
    }

    void getPKWarScenario(int mode, int state, int redState) {
        switch (mode) {
            case MODE_SELECT -> showSelectScenario(state);
            case MODE_BATTLE -> showBattleScenario(state, redState);
            case MODE_RESULT -> showResultScenario(state);
        }
    }

    private void showSelectScenario(int state) {
        switch (state) {
            case STATE_FIRST ->
                    System.out.printf("%s : 널 이기러 왔다! \n\n레드 : 누구세요?\n",
                            ToAccount.currentUser.NickName);
            case STATE_SECOND ->
                    System.out.printf("레드의 %s을 처치했다!!!\n\n레드 : 나 잠깐만 1분 텀... \n",
                            defeatedName);
            case STATE_THIRD ->
                    System.out.printf("내 퐁캣몬이 %s를 맞고 쓰러졌다!!ㅠㅠ\n\n레드 : 근데 진짜 누구세요?... \n",
                            killedBy);
        }
    }

    private void showBattleScenario(int state, int redState) {
        playerActionScenario(state);
        redActionScenario(redState);
    }

    private void playerActionScenario(int state) {
        switch (state) {
            case STATE_FIRST -> System.out.printf("%s : 가라!! %s!! \n%s : 퐁퐁퐁!!!! \n\n",
                    ToAccount.currentUser.NickName,
                    playerMon.name,
                    playerMon.name);
            case STATE_SECOND -> System.out.printf("%s : %s!! %s!!! \n%s : 퐁옹~~~퐁퐁!!!! \n효과는 굉장했다!! \n\n",
                    ToAccount.currentUser.NickName,
                    playerMon.name,
                    playerMon.skillName,
                    playerMon.name);
            case STATE_THIRD -> System.out.printf("%s(는)은 자신을 회복했다! \n%s : 퐁퐁~ \n\n",
                    playerMon.name,
                    playerMon.name);
            case STATE_FOURTH -> System.out.printf("%s(는)은 피할 준비를 하고 있다! \n%s : 어디 한번 잡아퐁~\n\n",
                    playerMon.name,
                    playerMon.name);
            case STATE_FIFTH -> System.out.printf("%s(는)은 피하기를 거부했다! \n%s : 주인님... 저도 싸우고 싶습니다..\n\n",
                    playerMon.name,
                    playerMon.name);
            case STATE_SIXTH -> System.out.printf("%s : %s!! %s!!! \n%s : 퐁옹~~~퐁퐁!!!! \n%s는 피했다!! \n\n",
                    ToAccount.currentUser.NickName,
                    playerMon.name,
                    playerMon.skillName,
                    playerMon.name,
                    redMon.name);
        }
    }

    private void redActionScenario(int redState) {
        switch (redState) {
            case STATE_FIRST -> System.out.printf("레드 : 가라!! %s!! \n%s : 퐁퐁퐁!!!! \n",
                    redMon.name,
                    redMon.name);
            case STATE_SECOND -> System.out.printf("레드 : %s!! %s!!! \n%s : 퐁옹~~~퐁퐁!!!! \n효과는 굉장했다!! \n",
                    redMon.name,
                    redMon.skillName,
                    redMon.name);
            case STATE_THIRD -> System.out.printf("%s(는)은 자신을 회복했다! \n%s : 퐁퐁~ \n",
                    redMon.name,
                    redMon.name);
            case STATE_FOURTH -> System.out.printf("%s(는)은 피할 준비를 하고 있다! \n%s : 어디 한번 잡아퐁~\n",
                    redMon.name,
                    redMon.name);
            case STATE_FIFTH -> System.out.printf("레드 : %s!! %s!!! \n%s : 퐁옹~~~퐁퐁!!!! \n%S(는)은 피했다!! \n",
                    redMon.name,
                    redMon.skillName,
                    redMon.name,
                    playerMon.name);
        }
    }

    private void showResultScenario(int state) {
        if (state == STATE_FIRST) {
            System.out.printf("축하합니다 %s님! 당신은 레드를 꺾고 챔피언에 등극하셨습니다!\n",
                    ToAccount.currentUser.NickName);
        } else if (state == STATE_SECOND) {
            System.out.println("너무 아쉽네요... 탈락입니다.");
        }
    }
}
