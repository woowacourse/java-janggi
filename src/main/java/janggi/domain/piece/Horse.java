package janggi.domain.piece;

import janggi.domain.piece.movepath.FiniteMovePath;
import janggi.domain.piece.movepath.MovePath;
import java.util.Set;

public class Horse extends Piece {

    public Horse(Dynasty dynasty) {
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
    public PieceType pieceType() {
        return PieceType.HORSE;
    }

    @Override
    public int score() {
        return 5;
    }

    @Override
    protected boolean isKing() {
        return false;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new FiniteMovePath(Direction.UP, Direction.UP_LEFT_DIAGONAL),
                new FiniteMovePath(Direction.UP, Direction.UP_RIGHT_DIAGONAL),

                new FiniteMovePath(Direction.DOWN, Direction.DOWN_LEFT_DIAGONAL),
                new FiniteMovePath(Direction.DOWN, Direction.DOWN_RIGHT_DIAGONAL),

                new FiniteMovePath(Direction.RIGHT, Direction.UP_RIGHT_DIAGONAL),
                new FiniteMovePath(Direction.RIGHT, Direction.DOWN_RIGHT_DIAGONAL),

                new FiniteMovePath(Direction.LEFT, Direction.UP_LEFT_DIAGONAL),
                new FiniteMovePath(Direction.LEFT, Direction.DOWN_LEFT_DIAGONAL)
        );
    }
}
