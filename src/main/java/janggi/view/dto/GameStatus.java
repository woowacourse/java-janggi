package janggi.view.dto;

import janggi.model.Janggi;
import janggi.model.gimul.Piece;
import janggi.model.gimul.Byeong;
import janggi.model.gimul.diagonalMove.Ma;
import janggi.model.gimul.diagonalMove.Sang;
import janggi.model.gimul.straightMove.Cha;
import janggi.model.gimul.straightMove.Pho;
import janggi.model.gimul.palace.Jang;
import janggi.model.gimul.palace.Sa;
import janggi.model.board.position.Column;
import janggi.model.board.position.Position;
import janggi.model.board.position.Row;
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

    private static String renderBoard(Map<Position, Piece> board) {
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
            Map<Position, Piece> board,
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
            Map<Position, Piece> board,
            int row,
            int col
    ) {
        StringBuilder sb = new StringBuilder();
        Position position = new Position(Row.of(row), Column.of(col));

        String symbol = "·";

        if (board.containsKey(position)) {
            Piece gimul = board.get(position);

            if (gimul instanceof Ma) {
                symbol = "마";
            }
            if (gimul instanceof Sang) {
                symbol = "상";
            }
            if (gimul instanceof Cha) {
                symbol = "차";
            }
            if (gimul instanceof Pho) {
                symbol = "포";
            }
            if (gimul instanceof Jang) {
                symbol = "장";
            }
            if (gimul instanceof Sa) {
                symbol = "사";
            }
            if (gimul instanceof Byeong) {
                symbol = "병";
            }
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
