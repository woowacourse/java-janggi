package view;

import java.util.Map;
import model.piece.Piece;
import model.position.Column;
import model.position.Position;
import model.position.Row;

public class JanggiPiecesView {

    public void printJanggiStart() {
        System.out.println("장기 시작");
    }

    public void showCurrentPositionOfPieces(Map<Position, Piece> pieces) {
        System.out.println("  １２３４５６７８９");
        for (Column column : Column.values()) {
            loopForRow(pieces, column);
        }
    }

    private void loopForRow(Map<Position, Piece> pieces, Column column) {
        System.out.print(column.getValue() + " ");
        for (Row row : Row.values()) {
            printPieceByPosition(pieces, column, row);
        }
        System.out.println();
    }

    private void printPieceByPosition(Map<Position, Piece> pieces, Column column, Row row) {
        Piece piece = pieces.get(new Position(column, row));
        if (piece == null) {
            System.out.print("－");
            return;
        }
        System.out.print(piece.getName());
    }

}
