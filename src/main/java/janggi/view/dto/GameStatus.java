package janggi.view.dto;

import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.view.mapping.PieceSymbol;
import java.util.Map;

public record GameStatus(
        String board,
        String team
) {
    private static final String TOP_LINE = "    1  2  3  4  5  6  7  8  9\n  ┌───────────────────────────┐\n";
    private static final String BOTTOM_LINE = "  └───────────────────────────┘\n";
    private static final String LEFT_LINE = " |";
    private static final String RIGHT_LINE = "|\n";

    public static GameStatus from(Janggi janggi) {
        return new GameStatus(
                renderBoard(janggi.getBoard().getBoardInfo()),
                renderTeam(janggi.getCurrentTeam())
        );
    }

    private static String renderBoard(Map<Position, Piece> board) {
        StringBuilder sb = new StringBuilder(TOP_LINE);

        for (int row = Row.START; row <= Row.END; row++) {
            renderBoardRow(sb, board, row);
        }

        return sb.append(BOTTOM_LINE)
                .toString();
    }

    private static void renderBoardRow(
            StringBuilder sb,
            Map<Position, Piece> board,
            int row
    ) {
        int displayRow = row;

        if (row == 10) {
            displayRow = 0;
        }

        sb.append(displayRow).append(LEFT_LINE);

        for (int col = Column.START; col <= Column.END; col++) {
            renderBoardColumn(sb, board, row, col);
        }

        sb.append(RIGHT_LINE);
    }

    private static void renderBoardColumn(
            StringBuilder sb,
            Map<Position, Piece> board,
            int row,
            int col
    ) {
        Position position = new Position(Row.of(row), Column.of(col));

        String symbol = "·";

        if (board.containsKey(position)) {
            Piece piece = board.get(position);
            symbol = PieceSymbol
                    .from(piece.getPieceType())
                    .getSymbol();
        }

        sb.append(" ")
                .append(String.format("%-2s", symbol));
    }

    private static String renderTeam(Team team) {
        if (team == Team.CHO) {
            return "초";
        }

        return "한";
    }
}
