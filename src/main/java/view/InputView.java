package view;

import java.util.Scanner;
import direction.Point;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    public static final int COLUMN_INDEX = 0;
    public static final int ROW_INDEX = 1;

    public static Point requestMoveStartPosition() {
        System.out.println("움직일 말을 알려주세요.");
        String from = scanner.nextLine();

        String[] rawFromPoint = from.split(" ");
        return new Point(
                Integer.parseInt(rawFromPoint[COLUMN_INDEX]),
                Integer.parseInt(rawFromPoint[ROW_INDEX])
        );
    }

    public static Point requestMoveEndPosition() {
        System.out.println("도착지를 알려주세요.");
        String to = scanner.nextLine();

        String[] rawToPoint = to.split(" ");
        return new Point(
                Integer.parseInt(rawToPoint[COLUMN_INDEX]),
                Integer.parseInt(rawToPoint[ROW_INDEX])
        );
    }
}
