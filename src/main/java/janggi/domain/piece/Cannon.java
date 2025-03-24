package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.movement.EndlessMovePath;
import janggi.domain.piece.movement.MovePath;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            throw new IllegalArgumentException("목적지에 같은 나라의 기물이 있어 갈 수 없습니다.");
        }
        int countSamePieceWithoutDestination = piecesOnPath.countSamePieceWithoutDestination(this);
        if (countSamePieceWithoutDestination > 0) {
            throw new IllegalArgumentException("포를 뛰어넘거나 죽일수 없습니다.");
        }
        int countNotSamePieceWithoutDestination = piecesOnPath.countNotSamePieceWithoutDestination(this);
        return (countNotSamePieceWithoutDestination == 0 || countNotSamePieceWithoutDestination == 1) &&
                piecesOnPath.isNotSameDestination(this);
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof Cannon;
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
