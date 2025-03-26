package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.piece.movepath.MovePath;
import java.util.Set;

public class Guard extends Piece {

    public Guard(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            throw new IllegalArgumentException("목적지에 같은 나라의 기물이 있어 갈 수 없습니다.");
        }
        return piecesOnPath.isAllEmptyWithoutDestination();
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSameType(Piece piece) {
        return piece instanceof Guard;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of();
    }
}
