//343
package classes;
import java.util.Scanner;

class RockScissorPaper implements GameStage {

	private Scanner sc = new Scanner(System.in);
    private int win = 0, money = 0, chances = 10;

    private static final int SCISSOR = 1;
    private static final int ROCK = 2;
    private static final int PAPER = 3;
	private boolean finished = false;
	
	private static final String[] CHOICES = {"", "가위", "바위", "보"};

    @Override
    public void reset() {
        win = 0;
        money = 0;
        chances = 10;
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
	
	@Override
    public void mPrint() {
        printStageInfo();
        while (!finished) {
            int userChoice = getUserChoice();
            if (userChoice == -1) break; // q 입력 시 종료

            int computerChoice = getComputerChoice();
            String result = determineResult(userChoice, computerChoice);
            printResult(userChoice, computerChoice, result);

            updateGame(result);
            checkGameEnd();
        }
    }

    private void printStageInfo() {
        System.out.println("\n==== STAGE 1 : 가위바위보 ====\n");
		switch (ToAccount.currentUser.level) {
			case 0 -> System.out.println("처음 오셨군요! 환영합니다!\n");
			case 1 -> System.out.printf("%d번째 도전이시군요! 파이팅!\n",ToAccount.currentUser.challenged);
			case 2 -> System.out.println("챔피언 납신다! 길을 비켜라!\n");		
		}
    }

    private int getUserChoice() {
        System.out.println("남은 기회 : " + chances + "회 | 현재 승리 : " + win + "회 | 보유 머니 : " + money + "원");
        System.out.println("선택: 1.가위  2.바위  3.보  (q: 종료)");
        System.out.print("\n번호 입력: ");
        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("q")) {
				System.out.println("잘가세요.");
				System.exit(0);
        }

        try {
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > 3) {
                System.out.println("[1~3번 중 선택해주세요!]");
                return getUserChoice();
            }
            return choice;
        } catch (NumberFormatException e) {
            System.out.println("[숫자를 입력해주세요!]");
            return getUserChoice();
        }
    }

    private int getComputerChoice() {
        return (int) (Math.random() * 3) + 1; // 1~3
    }

    private String determineResult(int user, int comp) {
        if (user == comp) return "무승부";
        if ((user == SCISSOR && comp == PAPER) ||
            (user == ROCK && comp == SCISSOR) ||
            (user == PAPER && comp == ROCK)) {
            return "승리";
        } else {
            return "패배";
        }
    }

    private void printResult(int user, int comp, String result) {
        System.out.println("\n▶ 플레이어 : " + CHOICES[user] + "  VS  컴퓨터 : " + CHOICES[comp]);
        System.out.println("\n결과: " + result);

        switch (result) {
            case "승리" -> System.out.println("\n축하합니다! 승리했습니다.");
            case "패배" -> System.out.println("\n아쉽네요. 패배했습니다.");
            case "무승부" -> System.out.println("\n무승부입니다.");
        }
    }

    private void updateGame(String result) {
        chances--;
        switch (result) {
            case "승리" -> {win++; money += 1000;}
            case "무승부" -> money += 500;
            case "패배" -> money += 0;
        }
    }

    private void checkGameEnd() {
        if (chances <= 0) {
            System.out.println("\n게임 종료!");
            System.out.println("얻은 총 승리 횟수: " + win + "회");
            System.out.println("획득한 총 금액: " + money + "원");
			
			ToAccount.currentUser.victory += win;
			ToAccount.currentUser.money += money;
            
			// 다음 스테이지 진입 or 종료

            while(true) {
                System.out.println("\n다음 스테이지로 이동하려면 'start' 입력");
                String input = sc.nextLine().trim();
                if (input.equalsIgnoreCase("start")) {
                    reset(); // 초기화 후 재시작
                    finished = true;
                    return;
                } else {
                    System.out.println("\n저기요?");
                }
            }

        }
    }
}	

