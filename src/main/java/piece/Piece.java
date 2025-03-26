package piece;

import direction.Point;

public interface Piece {

    void move(Pieces pieces, Point destination);

    boolean isSamePoint(Point point);

    String getNickname();
}
