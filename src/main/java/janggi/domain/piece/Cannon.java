package janggi.domain.piece;

import janggi.domain.board.Point;
import janggi.domain.camp.Camp;
import janggi.domain.piece.type.MoveType;
import janggi.domain.piece.type.PieceType;
import java.util.Set;

public final class Cannon extends Piece {

    private static final int POSSIBLE_JUMP_OVER_PIECE_COUNT = 1;

    public Cannon(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMovementRule(MoveType moveType, Point from, Point to) {
        if (moveType.isPalace()) {
            validateStraightAndDiagonalMove(from, to);
            return;
        }
        validateStraightMove(from, to);
    }

    private void validateStraightMove(Point from, Point to) {
        if (!from.isHorizontallyAlignedWith(to) && !from.isVerticallyAlignedWith(to)) {
            throw new IllegalArgumentException("포는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    public void validateStraightAndDiagonalMove(Point from, Point to) {
        if (!from.isHorizontallyAlignedWith(to) && !from.isVerticallyAlignedWith(to) && !from.isDiagonallyAlignedWith(
                to)) {
            throw new IllegalArgumentException("포는 궁성안에서 수평, 수직 대각선으로만 움직여야 합니다.");
        }
    }

    @Override
    public void validateRouteObstacles(Set<Piece> piecesOnRoute) {
        validatePieceCount(piecesOnRoute);
        validateNotJumpOverCannon(piecesOnRoute);
    }

    private void validatePieceCount(Set<Piece> pieces) {
        if (pieces.size() != POSSIBLE_JUMP_OVER_PIECE_COUNT) {
            throw new IllegalArgumentException("포는 정확히 하나의 기물만 넘을 수 있습니다. 넘은 기물 수: %d".formatted(pieces.size()));
        }
    }

    private void validateNotJumpOverCannon(Set<Piece> pieces) {
        if (hasCannon(pieces)) {
            throw new IllegalArgumentException("포는 포를 넘을 수 없습니다.");
        }
    }

    private boolean hasCannon(Set<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.getPieceType() == this.getPieceType());
    }

    @Override
    public Set<Point> findRoute(MoveType moveType, Point from, Point to) {
        if (moveType.isPalace() && from.isDiagonallyAlignedWith(to)) {
            return from.findDiagonalPointsBetween(to);
        }
        if (from.isHorizontallyAlignedWith(to)) {
            return from.findHorizontalPointsBetween(to);
        }
        return from.findVerticalPointsBetween(to);
    }

    @Override
    public void validateCatch(Piece targetPiece) {
        super.validateCatch(targetPiece);
        if (getPieceType() == targetPiece.getPieceType()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CANNON;
    }
}
