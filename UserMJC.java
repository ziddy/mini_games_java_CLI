package classes;

import java.util.List;
import java.util.ArrayList;

class UserMJC {
    String ID;
    String NickName;
    String pw;
    boolean isTestro; // 에겐인지 테토인지?
    int money;
    int victory;
    int challenged;
    int level;
    String review; // 챔피언 된 소감

    List<PongKatMon> playerMonsters;      // 플레이어가 가지고 있는 퐁캣몬 리스트
    List<PongKatMon> playerPongKatMon;    // 대전 시 플레이어가 꺼낸 퐁캣몬

    /*
    level 설명
    0 = 사용자
    1 = 챌저
    2 = 챔피언
    3 = 관리자

    isTestro 설명
    true = 에겐 모드
    false = 테토 모드
    */

    UserMJC(String _id, String _nickName, String _pw, boolean _isTestro, int _money,
            int _victory, int _challenged, int _level, String _review) {
        ID = _id;
        NickName = _nickName;
        pw = _pw;
        isTestro = _isTestro;
        money = _money;
        victory = _victory;
        challenged = _challenged;
        level = _level;
        review = _review;

        playerMonsters = new ArrayList<>();
        playerPongKatMon = new ArrayList<>();
    }

    // 초기 유저 목록
    public static List<UserMJC> list = new ArrayList<>();

    static {
        list.add(new UserMJC("pongpong", "퐁퐁이", "123456abc", true, 0, 0, 1, 0, ""));
        list.add(new UserMJC("winwin909", "한화팬", "2a4a3a5404", false, 0, 0, 12, 1, ""));
        list.add(new UserMJC("bowWow2424", "강호통", "1234", false, 1000000, 100, 6, 2, "반복해요 반복"));
        list.add(new UserMJC("bowser2323", "최비만", "5678", false, 1000000, 100, 2, 2, "쌀먹해요 쌀먹"));
    }
}
