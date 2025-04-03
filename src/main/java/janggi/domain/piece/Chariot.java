package janggi.domain.piece;

import janggi.domain.board.Point;
import janggi.domain.camp.Camp;
import janggi.domain.piece.type.MoveType;
import janggi.domain.piece.type.PieceType;
import java.util.Set;

public final class Chariot extends Piece {

    public Chariot(Camp camp) {
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
            throw new IllegalArgumentException("차는 수평 혹은 수직으로만 움직여야 합니다.");
        }
    }

    public void validateStraightAndDiagonalMove(Point from, Point to) {
        if (!from.isHorizontallyAlignedWith(to) && !from.isVerticallyAlignedWith(to)
                && !from.isDiagonallyAlignedWith(to)) {
            throw new IllegalArgumentException("차는 궁성안에서 수평, 수직 대각선으로만 움직여야 합니다.");
        }
    }

    @Override
    public void validateRouteObstacles(Set<Piece> piecesOnRoute) {
        if (!piecesOnRoute.isEmpty()) {
            throw new IllegalArgumentException("차는 기물을 넘어 이동할 수 없습니다.");
        }
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
    public PieceType getPieceType() {
        return PieceType.CHARIOT;
    }
}
