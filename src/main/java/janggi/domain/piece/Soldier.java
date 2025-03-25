package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;

import janggi.domain.moveRule.DefaultMoveRule;
import java.util.List;

public class Soldier extends Piece {
    private final static List<Direction> SOLDIER_DIRECTION = List.of(Direction.UP, Direction.DOWN, Direction.LEFT,Direction.RIGHT);

    public Soldier(PieceColor color) {
        super(color, PieceType.SOLDIER, DefaultMoveRule.getRule());
    }

    @Override
    public boolean isValidMovement(PiecePath path) {
        Direction direction = path.calculateDirection();

        if(this.color == PieceColor.RED && direction == Direction.UP) {
            return false;
        }
        if(this.color == PieceColor.BLUE && direction == Direction.DOWN) {
            return false;
        }

        return SOLDIER_DIRECTION.contains(direction);
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
