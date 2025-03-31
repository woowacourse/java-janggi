package janggi.domain.piece;

import janggi.domain.piece.movepath.EndlessMovePath;
import janggi.domain.piece.movepath.EndlessPalaceMovePath;
import janggi.domain.piece.movepath.MovePath;
import janggi.domain.piece.palace.ChuPalace;
import janggi.domain.piece.palace.HanPalace;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            return false;
        }
        if (piecesOnPath.isExistSamePiece(this)) {
            return false;
        }

        return piecesOnPath.countNotSamePieceWithoutDestination(this) == 1;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public int score() {
        return 7;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
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
                new EndlessPalaceMovePath(new HanPalace(), Direction.UP_RIGHT_DIAGONAL),
                new EndlessPalaceMovePath(new HanPalace(), Direction.UP_LEFT_DIAGONAL),
                new EndlessPalaceMovePath(new HanPalace(), Direction.DOWN_RIGHT_DIAGONAL),
                new EndlessPalaceMovePath(new HanPalace(), Direction.DOWN_LEFT_DIAGONAL),
                new EndlessPalaceMovePath(new ChuPalace(), Direction.UP_RIGHT_DIAGONAL),
                new EndlessPalaceMovePath(new ChuPalace(), Direction.UP_LEFT_DIAGONAL),
                new EndlessPalaceMovePath(new ChuPalace(), Direction.DOWN_RIGHT_DIAGONAL),
                new EndlessPalaceMovePath(new ChuPalace(), Direction.DOWN_LEFT_DIAGONAL));
    }
}
