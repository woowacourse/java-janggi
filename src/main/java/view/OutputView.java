package view;

import domain.board.Board;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.PieceAppearance;
import domain.position.Position;
import java.util.Map;

public class OutputView {
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;
    private final PieceAppearance appearance;

    public OutputView(PieceAppearance appearance) {
        this.appearance = appearance;
    }

    public void printBoard(Board board) {
        StringBuilder sb = new StringBuilder();
        Map<Position, Piece> boardState = board.currentPieces();
        for (int row = MAX_ROW; row >= 1; row--) {
            sb.append(row).append("\t");
            appendRow(sb, boardState, row);
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

    private void appendRow(StringBuilder sb, Map<Position, Piece> boardState, int row) {
        for (int col = 1; col <= MAX_COLUMN; col++) {
            Piece piece = boardState.getOrDefault(new Position(row, col), new EmptyPiece());
            sb.append(piece.display(appearance)).append("\t");
        }
    }
}
