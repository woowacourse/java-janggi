package piece;

import direction.Point;

public abstract class Piece {

    protected Point current;

    public Piece(Point current) {
        this.current = current;
    }

    public abstract void move(Pieces pieces, Point destination);

    public boolean isSamePoint(Point point) {
        return current.equals(point);
    }

    @Override
    public boolean equals(Object object) {
        return object != null && getClass() == object.getClass();
    }
}
