package janggi.view.view;

import janggi.model.Janggi;
import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import java.util.Map;

public record GameStatus(
        String board,
        String team
) {
    public static GameStatus from(Janggi janggi) {
        return new GameStatus(
                renderBoard(janggi.getBoard()),
                renderTeam(janggi.isChoTurn())
        );
    }

    private static String renderBoard(Map<Position, AbstractGimul> board) {
        StringBuilder sb = new StringBuilder();

        sb.append("    1  2  3  4  5  6  7  8  9\n");
        sb.append("  ┌───────────────────────────┐\n");

        int rowStart = 1;
        int rowEnd = 10;

        for (int row = rowStart; row <= rowEnd; row++) {
            sb.append(renderBoardRow(board, row));
        }

        sb.append("  └───────────────────────────┘\n");

        return sb.toString();
    }

    private static StringBuilder renderBoardRow(
            Map<Position, AbstractGimul> board,
            int row
    ) {
        StringBuilder sb = new StringBuilder();
        int colStart = 1;
        int colEnd = 9;

        int displayRow = row;

        if (row == 10) {
            displayRow = 0;
        }

        sb.append(displayRow).append(" │");

        for (int col = colStart; col <= colEnd; col++) {
            sb.append(renderBoardColumn(board, row, col));
        }
        sb.append("│\n");
        return sb;
    }

    private static StringBuilder renderBoardColumn(
            Map<Position, AbstractGimul> board,
            int row,
            int col
    ) {
        StringBuilder sb = new StringBuilder();
        Position position = new Position(Row.of(row), Column.of(col));

        String symbol = "·";

        if (board.containsKey(position)) {
            AbstractGimul gimul = board.get(position);
            symbol = gimul.getSymbol();
        }

        sb.append(" ").append(String.format("%-2s", symbol));
        return sb;
    }

    private static String renderTeam(boolean isChoTurn) {
        if (isChoTurn) {
            return "초";
        }

        return "한";
    }
}
