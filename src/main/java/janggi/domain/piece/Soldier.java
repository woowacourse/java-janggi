package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Soldier implements Piece {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.UP),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );

    private final Dynasty dynasty;

    public Soldier(Dynasty dynasty) {
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
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 목적지입니다."));

        List<Point> points = new ArrayList<>();
        Point curr = from;
        for (Direction direction : directions) {
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
        return piece instanceof Soldier;
    }

    private boolean canMove(List<Direction> path, Point from, Point to) {
        Point curr = from;
        for (Direction direction : path) {
            curr = curr.move(direction);
        }
        return curr.isSamePosition(to);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Soldier soldier = (Soldier) o;
        return dynasty == soldier.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dynasty);
    }
}
