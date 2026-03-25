package janggi.view;

import janggi.domain.Position;
import janggi.dto.BoardDTO;
import java.util.Map;

public class OutputView {
    private static final String EMPTY_CELL = "　　";
    private static final String COLUMN_INDEXES = "　　║　　０　　　　１　　　　２　　　　３　　　　４　　　　５　　　　６　　　　７　　　　８";
    private static final String DIVIDER = "　　║===============================================================";
    private static final String VERTICAL_LINE = "　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃";

    public void printBoardStatus(BoardDTO boardDto) {
        printLine(COLUMN_INDEXES);
        printLine(DIVIDER);
        for (int row = 0; row < 10; row++) {
            renderRow(row, boardDto.piecePosition());
            renderVerticalLine(row);
        }
    }

    private void renderRow(int row, Map<Position, String> status) {
        StringBuilder sb = new StringBuilder(toFullWidth(row) + "　║");
        for (int col = 0; col < 9; col++) {
            String piece = status.getOrDefault(new Position(row, col), EMPTY_CELL);
            sb.append("［").append(piece).append("］");
            appendLink(sb, col);
        }
        printLine(sb.toString());
    }

    private void appendLink(StringBuilder sb, int col) {
        if (col < 8) sb.append("━");
    }

    private void renderVerticalLine(int row) {
        if (row < 9) printLine(VERTICAL_LINE);
    }

    public void printLine(String message) {
        System.out.println(message);
    }

    public void printPlayerNameNotice(String sideName) {
        printLine(String.format(Message.PLAYER_NAME_NOTICE, sideName));
    }

    private String toFullWidth(int i) {
        return String.valueOf((char) ('０' + i));
    }
}
