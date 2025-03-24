package move;

import direction.Point;
import piece.Pieces;

public interface MovementRule {
    void validateDestination(Point from, Point to);
    void checkPaths(Pieces allPieces, Point from, Point to);
}
