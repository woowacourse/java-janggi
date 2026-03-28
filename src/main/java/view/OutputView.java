package view;

import domain.position.Position;
import domain.board.Board;
import domain.piece.Piece;

public class OutputView {

    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;

    public void printBoard(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int row = MAX_ROW; row >= 1; row--) {
            sb.append(row).append("\t");
            appendRow(sb, board, row);
            sb.append(System.lineSeparator());
        }
        appendColumnHeader(sb);
        System.out.println(sb);
    }

    private void appendColumnHeader(StringBuilder sb) {
        sb.append(" \t");
        for (int col = 1; col <= MAX_COLUMN; col++) {
            sb.append(col).append("\t");
        }
        sb.append(System.lineSeparator());
    }

    private void appendRow(StringBuilder sb, Board board, int row) {
        for (int col = 1; col <= MAX_COLUMN; col++) {
            Piece piece = board.pieceAt(new Position(row, col));
            sb.append(piece.toString()).append("\t");
        }
    }
}
