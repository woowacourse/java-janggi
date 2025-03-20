package view;

import janggiGame.board.Board;
import janggiGame.board.Dot;
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

    public List<Dot> readPieceMovement(Dynasty dynasty) {
        System.out.printf("""
                %n%s의 차례입니다. 말을 움직여주세요
                ex) 기물의 x좌표, 기물의 y좌표 > 이동할 x좌표, 이동할 y좌표
                """, dynasty.getName());

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

        Dot origin = Board.findBy(originX, originY);
        Dot destination = Board.findBy(destinationX, destinationY);

        return List.of(origin, destination);
    }

    public int readHanArrangement() {
        System.out.println("한나라는 배치 전략을 선택하세요.");
        System.out.println(ARRANGE_PROMPT);
        System.out.println();
        return Integer.parseInt(scanner.nextLine());
    }

    public int readChoArrangement() {
        System.out.println("초나라는 배치 전략을 선택하세요");
        System.out.println(ARRANGE_PROMPT);
        return Integer.parseInt(scanner.nextLine());
    }
}
