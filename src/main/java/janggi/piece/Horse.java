package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.view.PieceSymbol;
import java.util.Set;

public final class Horse extends Piece {

    private static final int STRAIGHT_STEP = 1;
    private static final int DIAGONAL_STEP = 1;
    private static final int HORSE_MOVE_DISTANCE = STRAIGHT_STEP + DIAGONAL_STEP;

    public Horse(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point from, Point to) {
        validateHorseMove(from, to);
    }

    private void validateHorseMove(Point from, Point to) {
        if (!isHorseMove(from, to)) {
            throw new IllegalArgumentException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }
    }

    private boolean isHorseMove(Point from, Point to) {
        int xDistance = from.xDistanceTo(to);
        int yDistance = from.yDistanceTo(to);
        return (xDistance == HORSE_MOVE_DISTANCE && yDistance == DIAGONAL_STEP)
                || (xDistance == DIAGONAL_STEP && yDistance == HORSE_MOVE_DISTANCE);
    }

    @Override
    public void validateRouteObstacles(Set<Piece> piecesOnRoute) {
        if (!piecesOnRoute.isEmpty()) {
            throw new IllegalArgumentException("마는 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    @Override
    public Set<Point> findRoute(Point from, Point to) {
        if (isHorseMovingHorizontally(from, to)) {
            return Set.of(from.nextHorizontalPointTo(to));
        }
        return Set.of(from.nextVerticalPointTo(to));
    }

    private boolean isHorseMovingHorizontally(Point from, Point to) {
        return from.xDistanceTo(to) == HORSE_MOVE_DISTANCE;
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.HORSE;
    }
}
