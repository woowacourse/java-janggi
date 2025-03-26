package view;

import java.util.Scanner;
import direction.Point;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    public static final int COLUMN_INDEX = 0;
    public static final int ROW_INDEX = 1;

    public static Point requestMoveStartPosition() {
        System.out.println("움직일 말의 좌표(column, row)를 입력하세요.(ex. 2, 10)");
        String from = scanner.nextLine();

        return rawPointToPoint(from);
    }

    public static Point requestMoveEndPosition() {
        System.out.println("말을 둘 좌표(column, row)를 알려주세요.(ex. 2, 10)");
        String to = scanner.nextLine();

        return rawPointToPoint(to);
    }

    private static Point rawPointToPoint(String to) {
        String[] rawToPoint = to.split(",");
        return new Point(
                Integer.parseInt(rawToPoint[COLUMN_INDEX].trim()),
                Integer.parseInt(rawToPoint[ROW_INDEX].trim())
        );
    }
}
