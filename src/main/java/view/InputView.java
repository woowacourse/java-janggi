package view;

import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import java.util.List;
import java.util.Scanner;

public class InputView {

    public static final String ARRANGE_PROMPT = """            
            1. 내상 배치
            2. 외상 배치
            3. 좌상 배치
            4. 우상 배치""";
    private final Scanner scanner = new Scanner(System.in);

    public int readHanArrangement() {
        System.out.println("한나라는 배치 전략을 선택하세요.");
        System.out.println(ARRANGE_PROMPT);
        return Integer.parseInt(scanner.nextLine());
    }

    public int readChoArrangement() {
        System.out.println("초나라는 배치 전략을 선택하세요");
        System.out.println(ARRANGE_PROMPT);
        return Integer.parseInt(scanner.nextLine());
    }

    public int getTurnOption(Dynasty currentDynasty) {
        System.out.printf("""
                %n%s의 차례입니다.
                수행할 옵션을 입력해주세요
                1. 기물 이동
                2. 머무르기
                3. 한 수 물러주기
                4. 점수 확인
                """, getDynastyName(currentDynasty));
        return Integer.parseInt(scanner.nextLine());
    }

    private String getDynastyName(Dynasty currentDynasty) {
        if(currentDynasty == Dynasty.CHO) {
            return "초나라";
        }
        return "한나라";
    }

    public List<Dot> readPieceMovement() {
        System.out.println("""
                말을 움직여주세요
                ex) 기물의 x좌표, 기물의 y좌표 > 이동할 x좌표, 이동할 y좌표""");
        String movementInput = scanner.nextLine();

        String[] originAndDestination = movementInput.split(">");
        String originInput = originAndDestination[0];
        String destinationInput = originAndDestination[1];

        String[] originXY = originInput.split(",");
        int originX = Integer.parseInt(originXY[0].trim());
        int originY = Integer.parseInt(originXY[1].trim());

        String[] destinationXY = destinationInput.split(",");
        int destinationX = Integer.parseInt(destinationXY[0].trim());
        int destinationY = Integer.parseInt(destinationXY[1].trim());

        Dot origin = Dot.getInstanceBy(originX, originY);
        Dot destination = Dot.getInstanceBy(destinationX, destinationY);

        return List.of(origin, destination);
    }

}
