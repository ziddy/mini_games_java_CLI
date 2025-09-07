package classes;

import java.util.List;
import java.util.ArrayList;

class PongKatMon {
	String name;
	String skillName;
	int attack;
	int hp;
	int max_hp;
	int needMoney;
	int needWin;
	boolean isAvoid;

	public PongKatMon(String name, String skillName, int attack, int hp, int max_hp, int needMoney, int needWin, boolean isAvoid) {
		this.name = name;
		this.skillName = skillName;
		this.attack = attack;
		this.hp = hp;
		this.max_hp = max_hp;
		this.needMoney = needMoney;
		this.needWin = needWin;
		this.isAvoid = isAvoid;
	}
	
	// 퐁캣몬 상점 리스트
	public static List<PongKatMon> list = new ArrayList<>(List.of(
		new PongKatMon("잉여퐁", "팔딱팔딱", 100, 1500, 1500,500, 1, false),
		new PongKatMon("퐁코리타", "박치기", 150, 1600, 1600,600, 1, false),
		new PongKatMon("퐁카츄", "정전기 공격", 200, 1600, 1600,700, 1, false),
		new PongKatMon("퐁이리", "쥐불놀이", 250, 1700, 1700,800, 2, false),
		new PongKatMon("마임퐁", "만지작 만지작", 400, 1800, 1800,900, 2, false),
		new PongKatMon("루주퐁", "불쾌한 뽀뽀", 500, 1900, 1900,1000, 3, false),
		new PongKatMon("어쿠스퐁", "콧구멍 쑤시기", 600, 2000, 2000,1200, 3, false),
		new PongKatMon("이상해퐁", "덩쿨싸대기", 800, 2200, 2200,1400, 4, false),
		new PongKatMon("근육퐁", "통수치기", 1000, 2600, 2600,1900, 4, false),
		new PongKatMon("니드퐁", "지진", 1500, 2900, 2900,2400, 5, false),
		new PongKatMon("코퐁리", "스톤샤워", 2000, 3400, 3400,2600, 6, false),
		new PongKatMon("리자퐁", "불대문자", 2700, 4000, 4000,3900, 8, false),
		new PongKatMon("갸라도퐁", "하이드로펌프", 3500, 5500, 5500,5000, 10, false),
		new PongKatMon("망냐퐁", "파괴광선", 10000, 10000, 10000,500, 30, false),
		new PongKatMon("뮤츠퐁", "걍죽어", 10000000, 1000000, 10000000,100000, 100, false)
	));
}