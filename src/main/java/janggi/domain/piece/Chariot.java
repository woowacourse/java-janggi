package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.movement.EndlessMovePath;
import janggi.domain.piece.movement.MovePath;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(Dynasty dynasty) {
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
    public boolean isSamePiece(Piece piece) {
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
