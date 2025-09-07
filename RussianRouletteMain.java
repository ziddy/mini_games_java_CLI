package classes;

import java.util.Scanner;
import java.util.Random;

class RussianRouletteMain implements GameStage {

	private final Scanner sc = new Scanner(System.in);
	private final Random rand = new Random();

	private boolean finished = false;
	private boolean banned = false;

	private int bulletPos;   // 총알 위치 (0~5)
	private int cylinderPos; // 현재 실린더 위치 (0~5)
	private boolean playerTurn;

	private int finishCoin = 0;
	private int finishWin = 0;

	public RussianRouletteMain() {
		reset();
	}

	@Override
	public void reset() {
		finished = false;
		banned = false;
		bulletPos = rand.nextInt(6); // 총알 위치 랜덤
		cylinderPos = 0;             // 시작 위치 0
		finishCoin = 0;
		finishWin = 0;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public boolean isBanned() {
		return banned;
	}

	/** 코인 선택 */
	private boolean coinToss() {
		while (true) {
			System.out.print("\n코인의 앞/뒤를 선택하세요 (앞/뒤): ");
			String input = sc.nextLine().trim();
			if (input.equals("앞")) return rand.nextInt(2) == 0;
			else if (input.equals("뒤")) return rand.nextInt(2) == 1;
			else System.out.println("잘못 입력하셨습니다. 다시 선택해주세요.");
		}
	}

	/** 메인 실행 */
	@Override
	public void mPrint() {
		System.out.println("=== 러시안 룰렛 ===");
		System.out.println("START / QUIT");

		while (true) {
			System.out.print("선택: ");
			String menu = sc.nextLine().trim();

			if (menu.equalsIgnoreCase("START")) {
				playerTurn = coinToss();
				System.out.println(playerTurn ? "\n플레이어 선공!\n" : "\n플레이어 후공!\n");
				gameLoop();
				break;

			} else if (menu.equalsIgnoreCase("QUIT")) {
				System.out.println("게임을 종료합니다.");
				banned = true;
				break;

			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}

	/** 게임 루프 */
	private void gameLoop() {
		while (!finished && !banned) {
			if (playerTurn) handlePlayerTurn();
			else handleComTurn();
			playerTurn = !playerTurn; // 턴 전환
		}
	}

	/** 방아쇠 당기기 */
	private boolean pullTrigger() {
		boolean fired = (cylinderPos == bulletPos);
		cylinderPos = (cylinderPos + 1) % 6; // 다음 칸으로 회전
		return fired;
	}

	/** 플레이어 턴 */
	private void handlePlayerTurn() {
		while (true) {
			System.out.print("방아쇠를 당기려면 'shot', 종료하려면 'quit' 입력: ");
			String input = sc.nextLine().trim();

			if (input.equalsIgnoreCase("shot")) {
				System.out.println("\n플레이어가 방아쇠를 당겼습니다...");
				if (pullTrigger()) {
					playerDied();
				} else {
					System.out.println("휴… 총알이 나오지 않았습니다.");
				}
				break;

			} else if (input.equalsIgnoreCase("quit")) {
				banned = true;
				break;

			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}

	/** 컴퓨터 턴 */
	private void handleComTurn() {
		System.out.println("\n컴퓨터가 방아쇠를 당깁니다...");
		if (pullTrigger()) {
			comDied();
		} else {
			System.out.println("컴퓨터는 살아남았습니다.\n");
		}
	}

	/** 플레이어 사망 */
	private void playerDied() {
		System.out.println("\n플레이어가 사망하였습니다.");
		ToAccount.currentUser.level = 1;
		ToAccount.currentUser.challenged++;
		banned = true;
		finished = true;
	}

	/** 컴퓨터 사망 */
	private void comDied() {
		finishCoin = 50000;
		finishWin = 10;
		System.out.println("\n컴퓨터가 사망하였습니다. 보상 획득!");
		System.out.printf("￦ %,d 원, 승 %d 획득\n", finishCoin, finishWin);

		while (true) {
			System.out.print("\n다음 단계로 넘어가고 싶으면 '다음'이라 입력해주세요: ");
			String next = sc.nextLine().trim();
			if (next.equals("다음")) {
				finished = true;
				return;
			} else {
				System.out.println("입력이 올바르지 않습니다. 다시 입력해주세요.");
			}
		}
	}
}
