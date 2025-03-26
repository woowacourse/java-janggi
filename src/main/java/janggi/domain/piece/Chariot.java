package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.movepath.EndlessMovePath;
import janggi.domain.piece.movepath.MovePath;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(Dynasty dynasty) {
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
        return piece instanceof Chariot;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new EndlessMovePath(Direction.UP),
                new EndlessMovePath(Direction.DOWN),
                new EndlessMovePath(Direction.RIGHT),
                new EndlessMovePath(Direction.LEFT));
    }
}
