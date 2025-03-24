package janggi.piece;

import janggi.board.Board;
import janggi.exception.ErrorException;
import janggi.position.Position;
import java.util.HashSet;
import java.util.Set;

public final class Cannon extends Piece {

    private static final int POSSIBLE_JUMP_OVER_PIECE_COUNT = 1;

    private final Board board;

    public Cannon(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Position fromPosition, Position toPosition) {
        validateLinearMove(fromPosition, toPosition);
        validateJumpOverOnePiece(fromPosition, toPosition);
    }

    private void validateLinearMove(Position fromPosition, Position toPosition) {
        if (!fromPosition.isHorizontal(toPosition) && !fromPosition.isVertical(toPosition)) {
            throw new ErrorException("포는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    private void validateJumpOverOnePiece(Position fromPosition, Position toPosition) {
        Set<Piece> pieces = board.getPiecesByPosition(findRoute(fromPosition, toPosition));
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
                .anyMatch(piece -> piece.getPieceSymbol() == this.getPieceSymbol());
    }

    private Set<Position> findRoute(Position fromPosition, Position toPosition) {
        boolean isHorizontal = fromPosition.isHorizontal(toPosition);
        if (isHorizontal) {
            return findHorizontalRoute(fromPosition.getY(), fromPosition.getX(), toPosition.getX());
        }
        return findVerticalRoute(fromPosition.getX(), fromPosition.getY(), toPosition.getY());
    }

    private Set<Position> findHorizontalRoute(int fixedY, int fromX, int toX) {
        Set<Position> route = new HashSet<>();
        int start = Math.min(fromX, toX) + 1;
        int end = Math.max(fromX, toX);
        for (int i = start; i < end; i++) {
            route.add(new Position(i, fixedY));
        }
        return route;
    }

    private Set<Position> findVerticalRoute(int fixedX, int fromY, int toY) {
        Set<Position> route = new HashSet<>();
        int start = Math.min(fromY, toY) + 1;
        int end = Math.max(fromY, toY);
        for (int i = start; i < end; i++) {
            route.add(new Position(fixedX, i));
        }
        return route;
    }

    @Override
    public void validateCatch(Piece otherPiece) {
        super.validateCatch(otherPiece);
        if (getPieceSymbol() == otherPiece.getPieceSymbol()) {
            throw new ErrorException("포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public Type getPieceSymbol() {
        return Type.CANNON;
    }
}
