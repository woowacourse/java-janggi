package model.piece;

import java.util.Set;
import model.OccupiedPositions;
import model.PieceIdentity;
import model.Position;

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
