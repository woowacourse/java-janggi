package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import java.util.Set;

public final class Cannon extends Piece {

    private static final int POSSIBLE_JUMP_OVER_PIECE_COUNT = 1;

    public Cannon(Camp camp) {
        super(camp, Type.CANNON);
    }

    public static Piece from(Camp camp) {
        return new Cannon(camp);
    }

    @Override
    public void validateMove(Movement movement, Board board) {
        validateLinearMove(movement);
        validateJumpOverOnePiece(movement, board);
    }

    private void validateLinearMove(Movement movement) {
        if (!movement.isHorizontal() && !movement.isVertical()) {
            throw new ErrorException("포는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private void validateJumpOverOnePiece(Movement movement, Board board) {
        Set<Piece> pieces = board.getPiecesByPosition(findRoute(movement));
        validatePieceCount(pieces);
        validateNotJumpOverCannon(pieces);
    }

    private void validatePieceCount(Set<Piece> pieces) {
        if (pieces.size() != POSSIBLE_JUMP_OVER_PIECE_COUNT) {
            throw new ErrorException("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: %d".formatted(pieces.size()));
        }
    }

    private void validateNotJumpOverCannon(Set<Piece> pieces) {
        if (hasCannon(pieces)) {
            throw new ErrorException("포는 포를 넘을 수 없습니다.");
        }
    }

    private boolean hasCannon(Set<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.getType() == this.getType());
    }

    private Set<Position> findRoute(Movement movement) {
        return movement.findRoute();
    }

    @Override
    public void validateCatch(Piece otherPiece) {
        super.validateCatch(otherPiece);
        if (getType() == otherPiece.getType()) {
            throw new ErrorException("포는 포를 잡을 수 없습니다.");
        }
    }
}
