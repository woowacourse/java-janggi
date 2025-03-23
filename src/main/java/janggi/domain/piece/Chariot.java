package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Chariot implements Piece {

    private static final Set<Direction> DIRECTIONS = Set.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    private final Dynasty dynasty;

    public Chariot(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public List<Point> movePath(Point from, Point to) {
        Direction direction = DIRECTIONS.stream()
                .filter(dir -> canMove(dir, from, to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없습니다."));

        List<Point> points = new ArrayList<>();
        Point curr = from;
        while (curr.isNotOutOfBoundary() && !curr.isSamePosition(to)) {
            curr = curr.move(direction);
            points.add(curr);
        }
        return points;
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            throw new IllegalArgumentException("목적지에 같은 나라의 기물이 있어 갈 수 없습니다.");
        }
        return piecesOnPath.isAllEmptyWithoutDestination();
    }

    @Override
    public boolean isDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof Chariot;
    }

    private boolean canMove(Direction direction, Point from, Point to) {
        Point curr = from;
        while (curr.isNotOutOfBoundary() && !curr.isSamePosition(to)) {
            curr = curr.move(direction);
        }
        return curr.isSamePosition(to);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
