package view;

import domain.Position;
import domain.board.Board;
import domain.piece.Piece;

public class OutputView {

    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;

    public void printBoard(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int row = MAX_ROW; row >= 1; row--) {
            appendRow(sb, board, row);
            sb.append(System.lineSeparator());
        }
        System.out.println(sb);
    }

    private void appendRow(StringBuilder sb, Board board, int row) {
        for (int col = 1; col <= MAX_COLUMN; col++) {
            Piece piece = board.pieceAt(new Position(row, col));
            sb.append(piece.toString()).append("\t");
        }
    }
}
