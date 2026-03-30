package domain.piece;

import domain.ErrorMessage;

import java.util.List;
import java.util.Optional;

public final class Cannon extends StraightMovingPiece {
    public Cannon(Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public void validateMove(List<Piece> blockedPieces, Optional<Piece> to) {
        if (blockedPieces.isEmpty()) {
            throw new IllegalStateException(ErrorMessage.CANNON_NEEDS_BRIDGE.getMessage());
        }

        if (blockedPieces.size() >= 2) {
            throw new IllegalStateException(ErrorMessage.CANNON_CANNOT_OVER_PIECES.getMessage());
        }

        Piece piece = blockedPieces.getFirst();

        if (piece.isCannon()) {
            throw new IllegalStateException(ErrorMessage.CANNON_CANNOT_OVER_CANNON.getMessage());
        }

        if (to.isPresent() && to.get().isCannon()) {
            throw new IllegalStateException(ErrorMessage.CANNON_CANNOT_TAKE_CANNON.getMessage());
        }
    }
}
