package classes;

import java.util.Scanner;

/**
 * FirstStore
 * 무기 상점을 구현한 게임 스테이지 클래스입니다.
 * - 활을 구매하면 양궁 게임으로 이동
 * - 리볼버를 구매하면 러시안 룰렛 게임으로 이동
 * - 포기하면 해당 스테이지가 종료됨
 */

class FirstStore implements GameStage {

 private final Scanner scanner = new Scanner(System.in);

 private String narrator = "여기까지 오느라 고생하셨습니다. 무기를 골라주세요.";
 private StageResult result = StageResult.NONE;
 private boolean banned = false;

 private static final int BOW_PRICE = 4000;
 private static final int REVOLVER_PRICE = 6000;

 @Override
 public void reset() {
 	narrator = "여기까지 오느라 고생하셨습니다. 무기를 골라주세요.";
 	result = StageResult.NONE;
 	banned = false;
 }

 @Override
 public boolean isFinished() {
 	return result == StageResult.BOW || result == StageResult.REVOLVER;
 }

 @Override
 public boolean isBanned() {
 	return banned;
 }

 @Override
 public void mPrint() {
 	printStoreUI();
 	String input = scanner.nextLine().trim();
 	processInput(input);
 }

 // 상점 UI 출력
 private void printStoreUI() {
 	System.out.println("\n☆ 무기고 ★");
 	System.out.printf("%s\n\n", narrator);
 	System.out.printf("YOUR CASH ￦ %,d원\n\n", ToAccount.currentUser.money);
 	System.out.printf("① ☆활☆ (%d원)\n", BOW_PRICE);
 	System.out.printf("② ★리볼버★ (%d원)\n", REVOLVER_PRICE);
 	System.out.println("\n─ 구매할 돈이 안되면 '포기'를 입력하세요. ─");
 }

 // 사용자 입력 처리
 private void processInput(String input) {
 	switch (input) {
 		case "포기" -> {
 			narrator = "구매를 포기하셨습니다.";
 			ToAccount.currentUser.level = 1;
 			ToAccount.currentUser.challenged++;
 			result = StageResult.GIVE_UP;
 			banned = true;
 		}
 		case "1" -> purchaseWeapon("활", BOW_PRICE, StageResult.BOW);
 		case "2" -> purchaseWeapon("리볼버", REVOLVER_PRICE, StageResult.REVOLVER);
 		default -> narrator = "잘못된 입력입니다. 다시 시도해주세요.";
 	}
 }

 // 무기 구매 처리
 private void purchaseWeapon(String weaponName, int price, StageResult weaponType) {
 	if (ToAccount.currentUser.money >= price) {
 		ToAccount.currentUser.money -= price;
 		narrator = weaponName + "을(를) 구매하셨습니다.";
 		result = weaponType;
 	} else {
 		narrator = "금액이 부족합니다.";
 	}
 }

 // 외부에서 결과 확인 (다음 스테이지 분기용)
 	public StageResult getResult() {
 		return result;
 	}
 }
