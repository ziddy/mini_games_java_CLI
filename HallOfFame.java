package classes;

import java.util.List;
import java.util.ArrayList;

class HallOfFame {
    String name;      // 이름
    String ID;
    int challenged;   // 도전 횟수
    String review;    // 소감

    HallOfFame(String name, String ID, int challenged, String review) {
        this.name = name;
        this.ID = ID;
        this.challenged = challenged;
        this.review = review;
    }

    // Hall of Fame 리스트
    public static List<HallOfFame> list = new ArrayList<>();

    static {
        list.add(new HallOfFame("강호통", "bowToMe2424", 6, "반복해요 반복"));
        list.add(new HallOfFame("최비만", "bowToMe2323", 2, "쌀먹해요 쌀먹"));
    }
}
