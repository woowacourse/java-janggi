package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.movement.FiniteMovePath;
import janggi.domain.piece.movement.MovePath;
import java.util.Set;

public class ChuSoldier extends Piece {

    public ChuSoldier() {
        super(Dynasty.CHU);
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            throw new IllegalArgumentException("목적지에 같은 나라의 기물이 있어 갈 수 없습니다.");
        }
        return piecesOnPath.isAllEmptyWithoutDestination();
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof ChuSoldier;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new FiniteMovePath(Direction.UP),
                new FiniteMovePath(Direction.LEFT),
                new FiniteMovePath(Direction.RIGHT)
        );
    }
}
