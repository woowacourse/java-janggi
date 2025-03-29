package janggi.domain.piece;

import janggi.domain.piece.movepath.EndlessMovePath;
import janggi.domain.piece.movepath.EndlessPalaceMovePath;
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
    public PieceType pieceType() {
        return PieceType.CHARIOT;
    }

    @Override
    public int score() {
        return 13;
    }

    @Override
    protected boolean isKing() {
        return false;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new EndlessMovePath(Direction.UP),
                new EndlessMovePath(Direction.DOWN),
                new EndlessMovePath(Direction.RIGHT),
                new EndlessMovePath(Direction.LEFT),
                new EndlessPalaceMovePath(Direction.UP_RIGHT_DIAGONAL),
                new EndlessPalaceMovePath(Direction.UP_LEFT_DIAGONAL),
                new EndlessPalaceMovePath(Direction.DOWN_RIGHT_DIAGONAL),
                new EndlessPalaceMovePath(Direction.DOWN_LEFT_DIAGONAL));
    }
}
