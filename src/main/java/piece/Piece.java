package piece;

import direction.Direction;
import direction.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private final PieceType pieceType;
    private Point currentPosition;

    public Piece(PieceType pieceType, Point currentPosition) {
        this.pieceType = pieceType;
        this.currentPosition = currentPosition;
    }

    protected Piece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getSide() {
        return pieceType.getSide();
    }

    public String getName() {
        return pieceType.getExpression();
    }

    public boolean isEqualPositionWith(Point targetPoint) {
        return currentPosition.equals(targetPoint);
    }

    public abstract void validateDestination(Point from, Point to);
    public abstract void checkPaths(Pieces allPieces, Point from, Point to);

    public void move(Point to) {
        currentPosition = to;
    }

    protected List<Point> findStraightPaths(Point from, Point to) {
        Direction direction = Direction.find(from, to);
        List<Point> paths = new ArrayList<>();
        Point current = new Point(from.x(), from.y());
        current = current.apply(direction);
        while(!current.equals(to)) {
            paths.add(current);
            current = current.apply(direction);
        }
        return paths;
    }

    protected void validateStraightDestination(Point from, Point to) {
        if(from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException();
        }
    }

    protected void validateNotSamePosition(Point from, Point to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException();
        }
    }
}
