package view;

import domain.Position;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import java.util.Map;

public class OutputView {

    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;
    private static final Piece EMPTY = new EmptyPiece();

    public void printBoard(Map<Position, Piece> board) {
        StringBuilder sb = new StringBuilder();
        for (int row = MAX_ROW; row >= 1; row--) {
            for (int col = 1; col <= MAX_COLUMN; col++) {
                Piece piece = board.getOrDefault(new Position(row, col), EMPTY);
                sb.append(piece.toString()).append("\t");
            }
            sb.append(System.lineSeparator());
        }
        System.out.println(sb);
    }
}
