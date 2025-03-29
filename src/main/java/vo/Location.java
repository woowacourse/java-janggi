package vo;

import domain.board.Point;
import domain.pieces.Piece;

public record Location(Point point, Piece piece) {

    public String getPiece() {
        return piece.getType().name();
    }

    public int getRow() {
        return point.row();
    }

    public int getColumn() {
        return point.column();
    }

    public int getPlayerId() {
        return piece.getPlayerId();
    }
}
