package move;

import direction.Point;
import piece.Pieces;

public interface MovementRule {
    Point move(Pieces pieces, Point from, Point to);
}
