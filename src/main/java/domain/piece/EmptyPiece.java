package domain.piece;

import domain.position.Position;

public class EmptyPiece implements Piece {

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }

    @Override
    public boolean isAlly(Piece other) {
        return false;
    }

    @Override
    public String display(PieceAppearance colorizer) {
        return colorizer.colorizeEmpty();
    }

    @Override
    public String toString() {
        return PieceType.EMPTY.name();
    }
}
