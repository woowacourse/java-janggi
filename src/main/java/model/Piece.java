package model;

import java.util.Set;

public abstract class Piece {
    private final PieceIdentity pieceIdentity;

    protected Piece(PieceIdentity pieceIdentity) {
        this.pieceIdentity = pieceIdentity;
    }

    public PieceIdentity identity() {
        return pieceIdentity;
    }

    public abstract Set<Position> calculateMovablePositions(
            Position startPosition,
            OccupiedPositions occupiedPositions
    );
}
