package classes;

import java.util.Scanner;

public class GameStart {
    private final Scanner sc = new Scanner(System.in);
    private final ToAccount account = new ToAccount(sc);
    private final RockScissorPaper rsp = new RockScissorPaper();
    private final YachtDice yd = new YachtDice();
    private final FirstStore fs = new FirstStore();
    private final ArcheryGame arc = new ArcheryGame();
    private final RussianRouletteMain rr = new RussianRouletteMain();
    private final SecondStore ss = new SecondStore();
    private final PongKatMonWar pk = new PongKatMonWar();
    private final SignUp signUp = new SignUp();

    private Stage stage = Stage.RSP;  // 현재 진행 스테이지

    /** 전체 게임 상태 초기화 */
    private void resetGame() {
        rsp.reset();
        yd.reset();
        fs.reset();
        arc.reset();
        rr.reset();
        ss.reset();
        pk.reset();
        account.resetVerification(); // 로그인 상태 초기화
        stage = Stage.RSP;            // 스테이지 초기화
    }

    /** 게임 진행 후 탈락 여부 반환 */
    private boolean proceedStage(GameStage game) {
        game.mPrint();
        return game.isBanned();
    }

    /** 메인 메뉴로 돌아가기 공통 처리 */
    private void backToMenu(MainMenu menu) {
        resetGame();
        menu.resetPrompt();
    }

    /** 게임 로그인/회원가입 처리 */
    private boolean handleLogin(MainMenu menu) {
        System.out.println("\n☆ 게임 시작 ★\n");
        System.out.println("① 로그인");
        System.out.println("② 회원가입\n");
        System.out.println("ⓠ 뒤로가기");
        System.out.println("\n - 선택해주세요! -");

        String prompt = sc.nextLine().trim();
        switch (prompt.toLowerCase()) {
            case "1" -> account.signIn();
            case "2" -> signUp.mPrint(account);
            case "q" -> {
                System.out.println("메인 메뉴로 돌아갑니다.\n");
                backToMenu(menu);
                return false; // 뒤로가기
            }
            default -> {
                System.out.println("1~2번 중 선택하거나 'q'를 입력해주세요.");
                return false;
            }
        }

        return account.isVerified(); // 로그인 성공 여부 반환
    }

    /** 게임 루프 */
    public void printMenu(MainMenu menu) {
        boolean firstTime = true;

        while (true) {

            // 로그인/회원가입 처리
            if (!account.isVerified()) {
                boolean loggedIn = handleLogin(menu);
                if (!loggedIn) {
                    return; // 뒤로가기 시 메인 메뉴로 복귀
                }
            }

            // stage 진행
            switch (stage) {
                case RSP -> {
                    proceedStage(rsp);
                    stage = Stage.YD;
                }
                case YD -> {
                    proceedStage(yd); // YachtDice
                    if (yd.isFinished()) { // 실제 게임이 끝난 경우에만 stage 이동
                        stage = Stage.FIRST_STORE;
                    } else {
                        // 게임 미종료 = 잘못된 입력 → stage 이동 금지
                        continue;
                    }
                }
                case FIRST_STORE -> {
                    boolean returnToMenu = proceedStage(fs);
                    if (returnToMenu) {
                        backToMenu(menu);
                        return;
                    }

                    switch (fs.getResult()) {
                        case BOW -> stage = Stage.ARCHERY;
                        case REVOLVER -> stage = Stage.REVOLVER;
                        case GIVE_UP -> {
                            backToMenu(menu);
                            return;
                        }
                    }
                }
                case ARCHERY -> {
                    boolean returnToMenu = proceedStage(arc);
                    if (returnToMenu) {
                        backToMenu(menu);
                        return;
                    }
                    if (arc.isFinished()) stage = Stage.SECOND_STORE;
                }
                case REVOLVER -> {
                    boolean returnToMenu = proceedStage(rr);
                    if (returnToMenu) {
                        backToMenu(menu);
                        return;
                    }
                    if (rr.isFinished()) stage = Stage.SECOND_STORE;
                }
                case SECOND_STORE -> {
                    boolean returnToMenu = proceedStage(ss);
                    if (returnToMenu) {
                        backToMenu(menu);
                        return;
                    }
                    if (ss.isFinished()) stage = Stage.PONGKATMON;
                }
                case PONGKATMON -> {
                    boolean returnToMenu = proceedStage(pk);
                    if (returnToMenu) {
                        backToMenu(menu);
                        return;
                    }
                    if (pk.isFinished()) {
                        backToMenu(menu);
                        return;
                    }
                }
            }

            // 첫 로그인 후 바로 stage 진행되도록
            if (firstTime) {
                firstTime = false;
                continue; // 로그인 후 stage 진행
            }
        }
    }
}
