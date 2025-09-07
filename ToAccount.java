package classes;
import java.util.Scanner;

class ToAccount {
	private Scanner sc = new Scanner(System.in);

	boolean backToGame = false;
	private boolean verified = false;
	private String prompt = "";

	public static UserMJC currentUser = null;

	public ToAccount(Scanner sc) {
        this.sc = sc;
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean isBackToGame() {
        return backToGame;
    }

    public void resetVerification() {
        verified = false;
    }

	 // 로그인
	 public void signIn() {
        String[] list = {"ID", "PW"};
        String[] info = new String[list.length];

        for (int i = 0; i < list.length; i++) {
            System.out.printf("【 %s 】 ", list[i]);
            info[i] = sc.nextLine().trim();
        }

        if (login(info[0], info[1])) {
            verified = true;
            backToGame = true;
            System.out.println("로그인 성공!");
        } else {
            System.out.println("일치하는 회원 정보가 없습니다.");
            backToGame = true;
        }
    }

	// 현재 사용자에 저장
	private boolean login(String inputId, String inputPw) {
        for (UserMJC user : UserMJC.list) {
            if (user.ID.equals(inputId) && user.pw.equals(inputPw)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }
}