package janggi.view;

import janggi.utils.Parser;
import janggi.view.reader.Console;

public final class InputView {

    private InputView() {
    }

    public static int readSetupCommand() {
        return Parser.parseInteger(Console.readLine());
    }

    public static String readPositionOfMovingPiece() {
        System.out.println("움직일 기물의 위치를 입력하세요. (형식: 행,열)");
        return Console.readLine();
    }
}
