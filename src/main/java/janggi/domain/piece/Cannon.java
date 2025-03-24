package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Cannon implements Piece {

    private static final Set<Direction> DIRECTIONS = Set.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    private final Dynasty dynasty;

    public Cannon(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    @Override
    public List<Point> movePath(Point from, Point to) {
        Direction direction = DIRECTIONS.stream()
                .filter(dir -> canMove(dir, from, to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없습니다."));

        List<Point> points = new ArrayList<>();
        Point curr = from;
        while (!curr.isOutOfBoundary() && !curr.isSamePosition(to)) {
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
        int countSamePieceWithoutDestination = piecesOnPath.countSamePieceWithoutDestination(this);
        if (countSamePieceWithoutDestination > 0) {
            throw new IllegalArgumentException("포를 뛰어넘거나 죽일수 없습니다.");
        }
        int countNotSamePieceWithoutDestination = piecesOnPath.countNotSamePieceWithoutDestination(this);
        return (countNotSamePieceWithoutDestination == 0 || countNotSamePieceWithoutDestination == 1) &&
                piecesOnPath.isNotSameDestination(this);
    }

    @Override
    public boolean isDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof Cannon;
    }

    private boolean canMove(Direction direction, Point from, Point to) {
        Point curr = from;
        while (!curr.isOutOfBoundary() && !curr.isSamePosition(to)) {
            curr = curr.move(direction);
        }
        return curr.isSamePosition(to);
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
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
