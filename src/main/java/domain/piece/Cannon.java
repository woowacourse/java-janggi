package domain.piece;

import domain.ErrorMessage;

import java.util.List;
import java.util.Optional;

public final class Cannon extends StraightMovingPiece {
    public Cannon(Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    public void validateMove(List<Piece> blockedPieces) {
        if (blockedPieces.isEmpty()) {
            throw new IllegalStateException(ErrorMessage.CANNON_NEEDS_BRIDGE.getMessage());
        }

        if (blockedPieces.size() >= 2) {
            throw new IllegalStateException(ErrorMessage.CANNON_CANNOT_OVER_PIECES.getMessage());
        }

        Piece piece = blockedPieces.getFirst();

        if (piece instanceof Cannon) {
            throw new IllegalStateException(ErrorMessage.CANNON_CANNOT_OVER_CANNON.getMessage());
        }

    }

    public void validateTarget(Optional<Piece> target) {
        target.ifPresent(piece -> {
            if (piece instanceof Cannon) {
                throw new IllegalStateException(ErrorMessage.CANNON_CANNOT_TAKE_CANNON.getMessage());
            }
        });
    }
}
