package janggi.view;

import janggi.utils.Parser;
import janggi.view.reader.Console;

public final class InputView {

    private InputView() {
    }

    public static int readGameSelection() {
        return Parser.parseInteger(Console.readLine());
    }

    public static String readGameName() {
        System.out.println("원하는 게임 이름을 입력하세요.");
        return Console.readLine().trim();
    }

    public static int readSetupCommand() {
        return Parser.parseInteger(Console.readLine());
    }

    public static String readFromPosition() {
        System.out.println("움직일 기물의 위치를 입력하세요. (형식: 행,열)");
        return Console.readLine();
    }

    public static String readTargetPosition() {
        System.out.println("선택한 기물의 목표 위치를 입력하세요. (형식: 행,열)");
        return Console.readLine();
    }
}
