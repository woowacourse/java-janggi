package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.view.PieceSymbol;
import java.util.HashSet;
import java.util.Set;

public final class Elephant extends Piece {

    private static final int STRAIGHT_STEP = 1;
    private static final int DIAGONAL_STEP = 2;
    private static final int ELEPHANT_MOVE_DISTANCE = STRAIGHT_STEP + DIAGONAL_STEP;

    public Elephant(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        validateElephantMove(fromPoint, toPoint);
    }

    private void validateElephantMove(Point fromPoint, Point toPoint) {
        if (!isElephantMove(fromPoint, toPoint)) {
            throw new IllegalArgumentException("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
        }
    }

    private boolean isElephantMove(Point fromPoint, Point toPoint) {
        int xDistance = fromPoint.getXDistanceFrom(toPoint);
        int yDistance = fromPoint.getYDistanceFrom(toPoint);
        return (xDistance == DIAGONAL_STEP && yDistance == ELEPHANT_MOVE_DISTANCE)
                || (xDistance == ELEPHANT_MOVE_DISTANCE && yDistance == DIAGONAL_STEP);
    }

    @Override
    public void validatePathObstacles(Set<Piece> piecesOnRoute) {
        if (!piecesOnRoute.isEmpty()) {
            throw new IllegalArgumentException("상은 기물을 넘어서 이동할 수 없습니다.");
        }
    }

    @Override
    public Set<Point> findRoute(Point from, Point to) {
        Point firstStepPoint = getFirstStepPoint(from, to);
        Point secondStepPoint = getSecondStepPoint(firstStepPoint, to);
        return buildRoute(firstStepPoint, secondStepPoint);
    }

    private Point getFirstStepPoint(Point from, Point to) {
        if (isElephantMovingHorizontally(from, to)) {
            return from.getNextHorizontalPointToward(to);
        }
        return from.getNextVerticalPointToward(to);
    }

    private Point getSecondStepPoint(Point firstStep, Point destination) {
        return firstStep.getCenterPointWith(destination);
    }

    private boolean isElephantMovingHorizontally(Point from, Point to) {
        return from.getXDistanceFrom(to) == ELEPHANT_MOVE_DISTANCE;
    }

    private Set<Point> buildRoute(Point firstStep, Point secondStep) {
        Set<Point> route = new HashSet<>();
        route.add(firstStep);
        route.add(secondStep);
        return route;
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.ELEPHANT;
    }
}
