package view;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceAppearance;
import domain.position.Position;

public class OutputView {
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 10;
    private static final int MIN_COLUMN = 1;
    private static final int MAX_COLUMN = 9;
    private final PieceAppearance appearance;

    public OutputView(PieceAppearance appearance) {
        this.appearance = appearance;
    }

    public void printBoard(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int row = MAX_ROW; row >= MIN_ROW; row--) {
            sb.append(row).append("\t");
            appendRow(sb, board, row);
            sb.append(System.lineSeparator());
        }
        appendColumnHeader(sb);
        System.out.println(sb);
    }

    public void printError(String message) {
        System.out.println(message);
    }

    private void appendColumnHeader(StringBuilder sb) {
        sb.append(" \t");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append(column).append("\t");
        }
        sb.append(System.lineSeparator());
    }

    private void appendRow(StringBuilder sb, Board board, int row) {
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Piece piece = board.pieceAt(new Position(row, column));
            sb.append(piece.display(appearance)).append("\t");
        }
    }
}
