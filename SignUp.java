package classes;

import java.util.Scanner;

public class SignUp {

	private Scanner sc = new Scanner(System.in);

	public void mPrint(ToAccount account) {

		System.out.println("\n\n\n[회원가입]");

		String ID = inputValidated("아이디", "[a-zA-Z0-9]+", 1, 10, null);
		String PWD = inputValidated("비밀번호", "[a-zA-Z0-9]+", 1, 10, ID);
		String nickname = inputValidated("닉네임", "[가-힣a-zA-Z0-9]+", 1, 10, null);
		String personality = selectPersonality();

		// 가입정보 확인
		System.out.println("\n[회원가입이 완료 되었습니다!!]");
		System.out.println("[입력하신 정보]");
		System.out.println("아이디 : " + ID);
		System.out.println("비밀번호 : " + PWD);
		System.out.println("닉네임 : " + nickname);
		System.out.println("성향 : " + (personality.equals("1") ? "에겐" : "테토"));

		account.backToGame = true;
	}

	// 입력 + 검증 메서드
	private String inputValidated(String fieldName, String pattern, int minLength, int maxLength, String forbidden) {
		String input = "";
		while (true) {
			System.out.println(fieldName + "를 입력해주세요:");
			System.out.print("> ");
			input = sc.nextLine().trim();

			if (input.isEmpty()) {
				System.out.println("[ !! 공백입력은 불가능합니다 !! ]");
			} else if (input.length() < minLength || input.length() > maxLength) {
				System.out.printf("[ !! %s는 %d~%d자 이내로 입력해주세요 !! ]\n", fieldName, minLength, maxLength);
			} else if (!input.matches(pattern)) {
				System.out.printf("[ !! %s 형식이 올바르지 않습니다 !! ]\n", fieldName);
			} else if (forbidden != null && input.equals(forbidden)) {
				System.out.printf("[ !! %s를 아이디와 다르게 입력해주세요 !! ]\n", fieldName);
			} else {
				break;
			}
		}
		return input;
	}

	// 성향 선택 메서드
	private String selectPersonality() {
		String choice = "";
		while (true) {
			System.out.println("당신의 성향은?? [ 1 = 에겐 | 2 = 테토 ]");
			System.out.print("> ");
			choice = sc.nextLine().trim();

			if (choice.equals("1") || choice.equals("2")) break;
			System.out.println("[ !! 숫자 1 또는 2만 입력해주세요 !! ]");
		}
		return choice;
	}
}
