package piece;

import direction.Point;

public class Chariot extends Piece {

    public Chariot(PieceType pieceType, Point point) {
        super(pieceType, point);
    }

    @Override
    public void validateDestination(Point from, Point to) {
        validateStraightDestination(from, to);
        validateNotSamePosition(from, to);
    }

    @Override
    public void checkPaths(Pieces pieces, Point from, Point to) {
        findStraightPaths(from, to).forEach(pieces::validateNotContainPiece);
    }
}
