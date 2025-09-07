package classes;
import java.util.Scanner;

 //스테이지 결과를 나타내는 enum
 public enum StageResult {
 	NONE,       // 아직 선택 안 함
 	BOW,        // 활 구매 → 양궁 게임으로
 	REVOLVER,   // 리볼버 구매 → 러시안룰렛으로
 	GIVE_UP     // 포기
 }