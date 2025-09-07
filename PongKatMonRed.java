package classes;

import java.util.List;
import java.util.ArrayList;

class PongKatMonRed {
    String name;
    String skillName;
    int attack;
    int hp;
    int max_hp;
    boolean isAvoid;

    public static List<PongKatMonRed> redPongKatMon = new ArrayList<>();
    public static List<PongKatMonRed> list = new ArrayList<>();

    PongKatMonRed(String name, String skillName, int attack, int hp, int max_hp, boolean isAvoid) {
        this.name = name;
        this.skillName = skillName;
        this.attack = attack;
        this.hp = hp;
        this.max_hp = max_hp;
        this.isAvoid = isAvoid;
    }

    // 초기화 메서드 추가
    public static void initializeList() {
        list.clear(); // 기존 리스트를 비우고
        list.add(new PongKatMonRed("퐁북왕", "파도타기", 750, 3250, 3250, false));
        list.add(new PongKatMonRed("괴력퐁", "돼지 두루치기", 800, 3300, 3300, false));
        list.add(new PongKatMonRed("후딘퐁", "사이코키네시스", 850, 3350, 3350, false));
        list.add(new PongKatMonRed("퐁리곤", "트라이어택", 900, 3400, 3400, false));
        list.add(new PongKatMonRed("퐁구리", "돌머리박치기", 950, 3450, 3450, false));
        list.add(new PongKatMonRed("잠만퐁", "코골이", 1500, 5000, 5000, false));
    }
}