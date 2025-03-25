package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class Guard extends Piece {
    private static final List<Direction> GENERAL_DIRECTION =
            List.of(Direction.UP,
                    Direction.DOWN,
                    Direction.LEFT,
                    Direction.RIGHT,
                    Direction.DOWN_LEFT,
                    Direction.DOWN_RIGHT,
                    Direction.UP_LEFT,
                    Direction.UP_RIGHT
            );

    public Guard(TeamColor color) {
        super(color, PieceType.GUARD, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(PiecePath path) {
        if (!path.isInPalacePath()) {
            return false;
        }
        return GENERAL_DIRECTION.stream()
                .anyMatch(path::canReachToDestination);
    }

    @Override
    public List<Position> findAllRoute(PiecePath path) {
        return List.of();
    }

    @Override
    public boolean isNotEmptyPiece() {
        return true;
    }
}
