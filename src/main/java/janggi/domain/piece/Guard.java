package janggi.domain.piece;

import janggi.domain.piece.movepath.MovePath;
import janggi.domain.piece.movepath.PalaceMovePath;
import janggi.domain.piece.palace.Palace;
import java.util.Set;

public class Guard extends Piece {

    public Guard(Dynasty dynasty) {
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
        return PieceType.GUARD;
    }

    @Override
    public int score() {
        return 3;
    }

    @Override
    protected boolean isKing() {
        return false;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new PalaceMovePath(Palace.from(dynasty), Direction.UP),
                new PalaceMovePath(Palace.from(dynasty), Direction.DOWN),
                new PalaceMovePath(Palace.from(dynasty), Direction.LEFT),
                new PalaceMovePath(Palace.from(dynasty), Direction.RIGHT),
                new PalaceMovePath(Palace.from(dynasty), Direction.UP_LEFT_DIAGONAL),
                new PalaceMovePath(Palace.from(dynasty), Direction.UP_RIGHT_DIAGONAL),
                new PalaceMovePath(Palace.from(dynasty), Direction.DOWN_LEFT_DIAGONAL),
                new PalaceMovePath(Palace.from(dynasty), Direction.DOWN_RIGHT_DIAGONAL));
    }
}
