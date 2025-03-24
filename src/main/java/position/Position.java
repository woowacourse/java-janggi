package position;

import piece.Blank;
import piece.Piece;

public final class Position {
    private final Column column;
    private final Row row;

    private final Piece piece;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
        this.piece = new Blank();
    }

    public Position(Column column, Row row, Piece piece) {
        this.column = column;
        this.row = row;
        this.piece = piece;
    }
}
