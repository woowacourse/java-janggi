package piece;

import direction.Point;

public class Chariot extends Piece {

    public Chariot(String name, Point point) {
        super(name, point);
    }

    @Override
    public void validateDestination(Point to) {
        validateStraightDestination(currentPosition, to);
        validateNotSamePosition(currentPosition, to);
    }

    @Override
    public void checkPaths(Pieces pieces, Point to) {
        findStraightPaths(currentPosition, to).forEach(pieces::validateNotContainPiece);
    }
}
