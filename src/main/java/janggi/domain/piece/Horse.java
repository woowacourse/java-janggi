package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Horse implements Piece {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.UP, Direction.UP_LEFT_DIAGONAL),
            List.of(Direction.UP, Direction.UP_RIGHT_DIAGONAL),

            List.of(Direction.DOWN, Direction.DOWN_LEFT_DIAGONAL),
            List.of(Direction.DOWN, Direction.DOWN_RIGHT_DIAGONAL),

            List.of(Direction.RIGHT, Direction.UP_RIGHT_DIAGONAL),
            List.of(Direction.RIGHT, Direction.DOWN_RIGHT_DIAGONAL),

            List.of(Direction.LEFT, Direction.UP_LEFT_DIAGONAL),
            List.of(Direction.LEFT, Direction.DOWN_LEFT_DIAGONAL)
    );

    private final Dynasty dynasty;

    public Horse(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public List<Point> movePath(Point from, Point to) {
        List<Direction> directions = PATHS.stream()
                .filter(path -> canMove(path, from, to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없습니다."));

        List<Point> points = new ArrayList<>();
        Point curr = from;
        for (Direction direction : directions) {
            curr = curr.move(direction);
            points.add(curr);
        }
        return points;
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof Horse;
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

    private boolean canMove(List<Direction> path, Point from, Point to) {
        Point curr = from;
        for (Direction direction : path) {
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
