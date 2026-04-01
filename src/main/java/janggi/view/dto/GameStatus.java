package janggi.view.dto;

import janggi.model.Janggi;
import janggi.model.piece.Piece;
import janggi.model.piece.Byeong;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.piece.diagonalMove.Sang;
import janggi.model.piece.straightMove.Cha;
import janggi.model.piece.straightMove.Pho;
import janggi.model.piece.palace.Jang;
import janggi.model.piece.palace.Sa;
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
            Piece piece = board.get(position);

            if (piece instanceof Ma) {
                symbol = "마";
            }
            if (piece instanceof Sang) {
                symbol = "상";
            }
            if (piece instanceof Cha) {
                symbol = "차";
            }
            if (piece instanceof Pho) {
                symbol = "포";
            }
            if (piece instanceof Jang) {
                symbol = "장";
            }
            if (piece instanceof Sa) {
                symbol = "사";
            }
            if (piece instanceof Byeong) {
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
