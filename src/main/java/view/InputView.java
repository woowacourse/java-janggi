package view;

import java.util.Scanner;
import direction.Point;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public static Point requestMoveStartPosition() {
        System.out.println("움직일 말을 알려주세요.");
        String from = scanner.nextLine();

        String[] rawFromPoint = from.split(" ");
        return new Point(
                Integer.parseInt(rawFromPoint[0]),
                Integer.parseInt(rawFromPoint[1])
        );
    }

    public static Point requestMoveEndPosition() {
        System.out.println("도착지를 알려주세요.");
        String to = scanner.nextLine();

        String[] rawToPoint = to.split(" ");
        return new Point(
                Integer.parseInt(rawToPoint[0]),
                Integer.parseInt(rawToPoint[1])
        );
    }
}
