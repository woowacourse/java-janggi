package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Movement;
import janggi.position.Position;
import java.util.Set;

public final class Cannon extends Piece {

    private static final int POSSIBLE_JUMP_OVER_PIECE_COUNT = 1;

    private final Board board;

    public Cannon(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Movement movement) {
        validateLinearMove(movement);
        validateJumpOverOnePiece(movement);
    }

    private void validateLinearMove(Movement movement) {
        if (!movement.isHorizontal() && !movement.isVertical()) {
            throw new ErrorException("포는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private void validateJumpOverOnePiece(Movement movement) {
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

    @Override
    public Type getType() {
        return Type.CANNON;
    }
}
