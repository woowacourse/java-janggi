package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.movepath.FiniteMovePath;
import janggi.domain.piece.movepath.MovePath;
import java.util.Set;

public class Elephant extends Piece {

    public Elephant(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            return false;
        }
        return piecesOnPath.isAllEmptyWithoutDestination();
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSameType(Piece piece) {
        return piece instanceof Elephant;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new FiniteMovePath(Direction.UP, Direction.UP_LEFT_DIAGONAL, Direction.UP_LEFT_DIAGONAL),
                new FiniteMovePath(Direction.UP, Direction.UP_RIGHT_DIAGONAL, Direction.UP_RIGHT_DIAGONAL),

                new FiniteMovePath(Direction.DOWN, Direction.DOWN_LEFT_DIAGONAL, Direction.DOWN_LEFT_DIAGONAL),
                new FiniteMovePath(Direction.DOWN, Direction.DOWN_RIGHT_DIAGONAL, Direction.DOWN_RIGHT_DIAGONAL),

                new FiniteMovePath(Direction.RIGHT, Direction.UP_RIGHT_DIAGONAL, Direction.UP_RIGHT_DIAGONAL),
                new FiniteMovePath(Direction.RIGHT, Direction.DOWN_RIGHT_DIAGONAL, Direction.DOWN_RIGHT_DIAGONAL),

                new FiniteMovePath(Direction.LEFT, Direction.UP_LEFT_DIAGONAL, Direction.UP_LEFT_DIAGONAL),
                new FiniteMovePath(Direction.LEFT, Direction.DOWN_LEFT_DIAGONAL, Direction.DOWN_LEFT_DIAGONAL)
        );
    }
}
