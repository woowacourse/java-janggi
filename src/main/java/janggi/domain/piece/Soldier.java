package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class Soldier extends Piece {
    private final static List<Direction> SOLDIER_DIRECTION =
            List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
    private final static List<Direction> IN_PALACE_DIRECTION =
            List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT,
                    Direction.DOWN_LEFT, Direction.DOWN_RIGHT, Direction.UP_LEFT, Direction.UP_RIGHT);

    public Soldier(PieceColor color) {
        super(color, PieceType.SOLDIER, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(PiecePath path) {
        Direction direction = path.calculateDirection();

        if(isBackMovement(direction, this.color)) {
            return false;
        }
        if(path.isInPalacePath() && IN_PALACE_DIRECTION.contains(direction)) {
            return true;
        }
        return SOLDIER_DIRECTION.contains(direction);
    }

    private boolean isBackMovement(Direction direction, PieceColor color) {
        if(color == PieceColor.BLUE && direction.getX() > 0) {
            return true;
        }
        if(color == PieceColor.RED && direction.getX() < 0) {
            return true;
        }
        return false;
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
