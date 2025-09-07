package classes;
import java.util.Scanner;

public interface GameStage {
	    void mPrint();         // 게임 진행 출력
	    boolean isFinished();  // 완료 여부
	    boolean isBanned();    // 중단 여부
	    void reset();          // 게임 상태 초기화
}