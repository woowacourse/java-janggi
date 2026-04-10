package core;

import pieces.Piece;
import position.Position;

public record MoveHistory(
    Position departure,
    Position destination,
    Piece movingPiece,
    Piece capturedPiece) {

    public boolean isCaptured() {
        return capturedPiece != null;
    }

    public int getDepartureRowIndex() {
        return departure.getRowIndex();
    }

    public int getDepartureColumnIndex() {
        return departure.getColumnIndex();
    }

    public int getDestinationRowIndex() {
        return destination.getRowIndex();
    }

    public int getDestinationColumnIndex() {
        return destination.getColumnIndex();
    }
}
