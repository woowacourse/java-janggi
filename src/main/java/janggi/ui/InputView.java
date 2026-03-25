package janggi.ui;

import janggi.domain.Point;
import janggi.util.Console;
import janggi.util.Parser;
import java.util.List;

public class InputView {

    private InputView() {
    }

    public static List<Point> readPoints() {
        return List.of(Parser.parsePoint(readFromPoint()),
                Parser.parsePoint(readToPoint())
        );
    }

    private static String readFromPoint() {
        System.out.println("움직일 기물의 출발지를 입력해 주세요 : ");
        return Console.readLine();
    }

    private static String readToPoint() {
        System.out.println("움직일 기물의 도착지를 입력해 주세요 : ");
        return Console.readLine();
    }
}
