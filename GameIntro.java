package classes;

class GameIntro {
	void printIntro(){
		System.out.println("[미니자바천국 게임소개]");
		System.out.println("\n[Game Introduce]");
		System.out.println("게임은 총 5단계로 진행됩니다.");
		System.out.println("마지막 게임까지 클리어 시 명예의 전당 등록 가능!");
		System.out.println("승리 시 코인 획득 → 상점에서 무기와 포켓몬 구매 → 마지막 게임까지 진행\n");

		System.out.println("=== STAGE ① : 가위바위보 ===");
		System.out.println("1 = 가위, 2 = 바위, 3 = 보 입력 → 총 10번 대결");
		System.out.println("승리: +1점, +1000코인 | 무승부: +500코인 | 패배: 없음\n");

		System.out.println("=== STAGE ② : 요트다이스 ===");
		System.out.println("플레이어와 컴퓨터가 주사위 5개 굴림");
		System.out.println("족보 선택 후 점수 비교 → 더 높은 점수 승리\n");

		System.out.println("=== CHOICE STAGE ===");
		System.out.println("두 개 게임 중 하나 선택 → 상점에서 무기 구매 후 진행\n");
		
		System.out.println("[러시안 룰렛]");
		System.out.println("동전 던져 선후공 결정 → 6/1 확률로 게임 시작");
		System.out.println("번갈아 총 쏘기 → 먼저 상대를 쓰러뜨리면 승리 + 보상");
		System.out.println("패배 시 탈락, quit 입력 시 게임 재시작\n");

		System.out.println("[양궁]");
		System.out.println("총 10번 기회, 날아오는 새 맞히기 → 승리 횟수 증가");
		System.out.println("보상: 1~3승 +4000원 | 4~6승 +6000원 | 7~9승 +9000원 | 10승 +15000원\n");

		System.out.println("=== STAGE ⑤ : 포켓몬 ===");
		System.out.println("모은 돈으로 상점에서 포켓몬 6마리 구매");
		System.out.println("등급 좋은 포켓몬일수록 구매 조건 높음");
		System.out.println("포켓몬 모두 확보 후 트레이너 레드와 대결 → 승리 시 게임 클리어!\n");
	};
 }
 