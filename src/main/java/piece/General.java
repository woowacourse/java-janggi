package piece;

import direction.Point;

public class General extends Piece {

    public General(PieceType pieceType, Point point) {
        super(pieceType, point);
    }

    @Override
    public void validateDestination(Point from, Point to) {
        if (from.x() + 1 < to.x() || from.x() - 1 > to.x() || from.y() + 1 < to.y() || from.y() - 1 > to.y()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    @Override
    public void checkPaths(Pieces allPieces, Point from, Point to) {

    }
}
