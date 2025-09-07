package classes;

import java.util.Scanner;

class MainMenu {
    private final GameIntro intro = new GameIntro();
    private final Scanner sc = new Scanner(System.in);
    private String prompt = "";

    // 메뉴 코드 상수
    private static final String MENU_INTRO = "1";
    private static final String MENU_HALL_OF_FAME = "2";
    private static final String GAME_START = "3";
    private static final String MENU_QUIT = "q";

    /** 명예의 전당 출력 */
    private void printHallOfFame() {
        for (int i = 0; i < HallOfFame.list.size(); i++) {
            HallOfFame hof = HallOfFame.list.get(i);
            System.out.println("⊂【 명예의 전당 챔피언 】⊃\n");
            System.out.printf("│  챔피언명 │     %s (%s) \n", hof.name, hof.ID);
            System.out.printf("│  도전횟수 │     %d회  \n", hof.challenged);
            System.out.printf("│  소   감 │     %s\n\n", hof.review);
        }
    }

    void printMenu() {
        System.out.println("\n☆ 레드를 잡아라 ★\n");

        System.out.println("① 게임소개");
        System.out.println("② 명예의 전당");
        System.out.println("③ 게임시작");
        System.out.println("\nⓠ 시스템종료\n");
        System.out.println("─  선택해주세요! ─\n");
        prompt = sc.nextLine().trim();

        switch (prompt) {
            case MENU_INTRO -> intro.printIntro();
            case MENU_HALL_OF_FAME -> printHallOfFame();
            case GAME_START -> System.out.println("게임 시작 준비 중..."); // 최소 표시
            default -> System.out.println("1~3번까지 메뉴 선택해주세요.\n");
        }

        if (prompt.equalsIgnoreCase(MENU_QUIT)) {
            System.out.println("시스템을 종료합니다.\n");
            System.exit(0);
        }
    }

    public String getPrompt() {
        return prompt;
    }

    public void resetPrompt() {
        this.prompt = "";
    }
}


class MainApp {
    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        GameStart gameStart = new GameStart();

        while (true) {
            menu.printMenu();                    // 메뉴 출력 + 입력
            String current = menu.getPrompt();   // 방금 입력 값 확인

            if (current.equals("3")) {
                gameStart.printMenu(menu);       // 게임 시작
                menu.resetPrompt();              // 게임 끝난 후 메인 메뉴 prompt 초기화
            }
        }
    }
}

