package piece;

import direction.Point;

public interface Piece {

    void move(Pieces pieces, Point destination);
}
