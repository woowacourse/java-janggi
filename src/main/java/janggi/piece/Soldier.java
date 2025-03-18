package janggi.piece;

import janggi.Camp;
import janggi.Point;

public final class Soldier extends Piece {

    public Soldier(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (fromPoint.equals(toPoint)) {
            throw new IllegalArgumentException("같은 위치로 이동할 수 없습니다.");
        }
        if (isBottom()) {
            if (toPoint.getY() < fromPoint.getY()) {
                throw new IllegalArgumentException("졸은 뒤로 갈 수 없습니다.");
            }
            if (Math.abs(toPoint.getY() - fromPoint.getY() + fromPoint.getX() - toPoint.getX()) != 1) {
                throw new IllegalArgumentException("졸은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
            }
            return;
        }
        if (fromPoint.getY() < toPoint.getY()) {
            throw new IllegalArgumentException("병은 뒤로 갈 수 없습니다.");
        }
        if (Math.abs(toPoint.getY() - fromPoint.getY() + fromPoint.getX() - toPoint.getX()) != 1) {
            throw new IllegalArgumentException("병은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }
}
