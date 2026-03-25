package janggi.view;

import janggi.dto.BoardSpot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputView {

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printNewLine() {
        System.out.println();
    }

    public void printStartMessage() {
        printMessage("장기 게임을 시작합니다.");
        printNewLine();
    }

    public void printBoard(List<BoardSpot> boardSpots) {
        System.out.println(makeBoard(boardSpots));
    }

    private String makeBoard(List<BoardSpot> boardSpots) {
        Map<String, String> boardSpotMap = makeBoardSpotMap(boardSpots);
        StringBuilder builder = new StringBuilder();
        builder.append(makeHeader());
        for (int y = 10; y >= 1; y--) {
            builder.append(makeRow(boardSpotMap, y));
        }
        return builder.toString();
    }

    private String makeHeader() {
        StringBuilder builder = new StringBuilder("    ");
        for (int x = 1; x <= 9; x++) {
            builder.append(String.format("%-3s", x));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private String makeRow(Map<String, String> boardSpotMap, int y) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%2d ", y));
        for (int x = 1; x <= 9; x++) {
            builder.append(String.format("%-3s", findPieceName(boardSpotMap, x, y)));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private Map<String, String> makeBoardSpotMap(List<BoardSpot> boardSpots) {
        Map<String, String> boardSpotMap = new HashMap<>();
        for (BoardSpot boardSpot : boardSpots) {
            boardSpotMap.put(boardSpot.position(), boardSpot.pieceName());
        }
        return boardSpotMap;
    }

    private String findPieceName(Map<String, String> boardSpotMap, int x, int y) {
        return boardSpotMap.getOrDefault(makeKey(x, y), ".");
    }

    private String makeKey(int x, int y) {
        return x + "," + y;
    }
}
