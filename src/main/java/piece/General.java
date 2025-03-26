package piece;

import direction.Point;

public class General extends Piece {

    public General(String name, Point point) {
        super(name, point);
    }

    @Override
    public void validateDestination(Point to) {
        if (currentPosition.x() + 1 < to.x() || currentPosition.x() - 1 > to.x()
                || currentPosition.y() + 1 < to.y() || currentPosition.y() - 1 > to.y()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    @Override
    public void checkPaths(Pieces allPieces, Point to) {

    }
}
