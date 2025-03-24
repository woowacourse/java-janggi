package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.view.PieceSymbol;
import java.util.Set;

public final class Soldier extends Piece {

    public Soldier(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (isBottom()) {
            validateJolMove(fromPoint, toPoint);
            return;
        }
        validateByeongMove(fromPoint, toPoint);
    }

    private void validateJolMove(Point fromPoint, Point toPoint) {
        if (toPoint.getY() < fromPoint.getY()) {
            throw new IllegalArgumentException("졸은 뒤로 갈 수 없습니다.");
        }
        if (Math.abs(toPoint.getY() - fromPoint.getY() + fromPoint.getX() - toPoint.getX()) != 1) {
            throw new IllegalArgumentException("졸은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    private void validateByeongMove(Point fromPoint, Point toPoint) {
        if (fromPoint.getY() < toPoint.getY()) {
            throw new IllegalArgumentException("병은 뒤로 갈 수 없습니다.");
        }
        if (Math.abs(toPoint.getY() - fromPoint.getY() + fromPoint.getX() - toPoint.getX()) != 1) {
            throw new IllegalArgumentException("병은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    @Override
    public void validatePathObstacles(Set<Piece> piecesOnRoute) {

    }

    @Override
    public Set<Point> findRoute(Point fromPoint, Point toPoint) {
        return Set.of();
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.SOLDIER;
    }
}
