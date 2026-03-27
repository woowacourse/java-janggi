package janggi.view;

import janggi.dto.BoardSpot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printNewLine() {
        System.out.println();
    }

    public static void printStartMessage() {
        printMessage("장기 게임을 시작합니다.");
        printNewLine();
    }

    public static void printBoard(List<BoardSpot> boardSpots) {
        System.out.println(makeBoard(boardSpots));
    }

    private static String makeBoard(List<BoardSpot> boardSpots) {
        Map<String, String> boardSpotMap = makeBoardSpotMap(boardSpots);
        StringBuilder builder = new StringBuilder();
        builder.append(makeHeader());
        for (int y = 10; y >= 1; y--) {
            builder.append(makeRow(boardSpotMap, y));
        }
        return builder.toString();
    }

    private static String makeHeader() {
        StringBuilder builder = new StringBuilder("    ");
        for (int x = 1; x <= 9; x++) {
            builder.append(String.format("%-3s", x));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private static String makeRow(Map<String, String> boardSpotMap, int y) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%2d ", y));
        for (int x = 1; x <= 9; x++) {
            builder.append(String.format("%-3s", findPieceName(boardSpotMap, x, y)));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private static Map<String, String> makeBoardSpotMap(List<BoardSpot> boardSpots) {
        Map<String, String> boardSpotMap = new HashMap<>();
        for (BoardSpot boardSpot : boardSpots) {
            boardSpotMap.put(boardSpot.position(), boardSpot.pieceName());
        }
        return boardSpotMap;
    }

    private static String findPieceName(Map<String, String> boardSpotMap, int x, int y) {
        return boardSpotMap.getOrDefault(makeKey(x, y), ".");
    }

    private static String makeKey(int x, int y) {
        return x + "," + y;
    }

    public static void printTurnNotice(String nowTurn) {
        printMessage(nowTurn + "의 차례입니다.");
    }

    public static void printAskPiecePosition() {
        printMessage("움직일 기물의 좌표를 입력해주세요.");
    }

    public static void printAskMovePosition(String nickname) {
        printMessage(nickname + "의 목적 좌표를 입력해주세요.");
    }
}
