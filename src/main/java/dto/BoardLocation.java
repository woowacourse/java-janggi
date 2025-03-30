package dto;

import domain.board.Point;
import domain.pieces.PieceDefinition;

public record BoardLocation(Point point, PieceDefinition piece, int playerId) {

    public String getPiece() {
        return piece.name();
    }

    public int getRow() {
        return point.row();
    }

    public int getColumn() {
        return point.column();
    }

    public int getPlayerId() {
        return playerId;
    }
}
