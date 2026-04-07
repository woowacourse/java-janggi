package janggi.ui;

import janggi.domain.Point;
import janggi.util.Console;
import janggi.util.Parser;
import java.util.List;

public class InputView {

    private static final String NEW_GAME_COMMAND = "1";
    private static final String LOAD_GAME_COMMAND = "2";

    private InputView() {
    }

    public static String readGameCommand() {
        System.out.println("장기 게임을 시작합니다.");
        System.out.println("새 게임은 1, 이어하기는 2 입력해 주세요 : ");
        return Console.readLine().trim();
    }

    public static Long readGameId() {
        System.out.println("불러올 게임 id를 입력해 주세요 : ");
        return Long.parseLong(Console.readLine().trim());
    }

    public static List<Point> readPoints() {
        return List.of(
                Parser.parsePoint(readFromPoint()),
                Parser.parsePoint(readToPoint())
        );
    }

    public static boolean isNewGameCommand(String command) {
        return NEW_GAME_COMMAND.equals(command);
    }

    public static boolean isLoadGameCommand(String command) {
        return LOAD_GAME_COMMAND.equals(command);
    }

    private static String readFromPoint() {
        System.out.println("움직일 기물의 출발지를 입력해 주세요 (예 : 1,2) : ");
        return Console.readLine();
    }

    private static String readToPoint() {
        System.out.println();
        System.out.println("움직일 기물의 도착지를 입력해 주세요 (예 : 1,4) : ");
        return Console.readLine();
    }
}
