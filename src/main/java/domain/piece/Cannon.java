package domain.piece;

import domain.ErrorMessage;
import domain.Path;

import java.util.List;

public final class Cannon extends StraightMovingPiece {
    public Cannon(Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    public void canMove(List<Path> paths, Piece to) {
        if (paths.isEmpty()) {
            throw new IllegalStateException(ErrorMessage.CANNON_NEEDS_BRIDGE.getMessage());
        }

        if (paths.size() >= 2) {
            throw new IllegalStateException(ErrorMessage.CANNON_CANNOT_OVER_PIECES.getMessage());
        }

        Piece piece = paths.getFirst().piece();

        if (piece.getPieceType() == PieceType.CANNON) {
            throw new IllegalStateException(ErrorMessage.CANNON_CANNOT_OVER_CANNON.getMessage());
        }

        if (to != null && to.getPieceType() == PieceType.CANNON) {
            throw new IllegalStateException(ErrorMessage.CANNON_CANNOT_TAKE_CANNON.getMessage());
        }
    }
}
