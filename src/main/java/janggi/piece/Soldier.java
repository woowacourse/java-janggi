package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.view.PieceSymbol;
import java.util.Set;

public final class Soldier extends Piece {

    private static final int SOLDIER_MOVE_DISTANCE = 1;

    public Soldier(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point from, Point to) {
        if (isJol()) {
            validateJolMove(from, to);
            return;
        }
        validateByeongMove(from, to);
    }

    private void validateJolMove(Point from, Point to) {
        if (from.isYGreaterThan(to)) {
            throw new IllegalArgumentException("졸은 뒤로 갈 수 없습니다.");
        }
        if (!isSoldierMove(from, to)) {
            throw new IllegalArgumentException("졸은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    private void validateByeongMove(Point from, Point to) {
        if (to.isYGreaterThan(from)) {
            throw new IllegalArgumentException("병은 뒤로 갈 수 없습니다.");
        }
        if (!isSoldierMove(from, to)) {
            throw new IllegalArgumentException("병은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    private boolean isSoldierMove(Point from, Point to) {
        int moveDistance = from.xDistanceTo(to) + from.yDistanceTo(to);
        return moveDistance == SOLDIER_MOVE_DISTANCE;
    }

    @Override
    public void validateRouteObstacles(Set<Piece> piecesOnRoute) {

    }

    @Override
    public Set<Point> findRoute(Point from, Point to) {
        return Set.of();
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.SOLDIER;
    }
}
