package janggi.presentation.ui;

import janggi.presentation.dto.MoveCommand;
import janggi.util.Console;
import janggi.util.Parser;

public class InputView {

    private InputView() {
    }

    public static MoveCommand readPoints() {
        return new MoveCommand(Parser.parsePoint(readFromPoint()),
                Parser.parsePoint(readToPoint())
        );
    }

    private static String readFromPoint() {
        System.out.print("움직일 기물의 출발지 좌표 입력해 주세요.(쉼표 기준으로 분리, ex. 0,0) : ");
        return Console.readLine();
    }

    private static String readToPoint() {
        System.out.print("움직일 기물의 도착지 좌표 입력해 주세요.(쉼표 기준으로 분리, ex. 0,1) : ");
        return Console.readLine();
    }
}
