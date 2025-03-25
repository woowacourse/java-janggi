package view;

import janggiGame.Position;
import janggiGame.piece.Dynasty;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class InputView {
    public static final String ARRANGE_PROMPT = """            
            1. 내상 배치
            2. 외상 배치
            3. 좌상 배치
            4. 우상 배치""";
    private static final ResourceBundle dynastyBundle = ResourceBundle.getBundle("dynasty");

    private final Scanner scanner = new Scanner(System.in);

    public List<Position> readPieceMovement(Dynasty dynasty) {
        System.out.printf("""
                %n%s의 차례입니다. 말을 움직여주세요
                ex) 기물의 x좌표, 기물의 y좌표 > 이동할 x좌표, 이동할 y좌표
                """, dynastyBundle.getString(dynasty.name()));

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

        Position origin = Position.of(originX, originY);
        Position destination = Position.of(destinationX, destinationY);

        return List.of(origin, destination);
    }

    public int readArrangementStrategyByDynasty(Dynasty dynasty) {
        System.out.printf("%s는 배치 전략을 선택하세요.", dynastyBundle.getString(dynasty.name()));
        System.out.println(ARRANGE_PROMPT);
        return Integer.parseInt(scanner.nextLine());
    }
}
