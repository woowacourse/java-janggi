package domain.direction;

import domain.position.Position;
import domain.position.PositionDelta;

import java.util.ArrayDeque;
import java.util.Queue;

public class Directions {
    private final Queue<Direction> directions;

    private Directions(Queue<Direction> directions) {
        this.directions = directions;
    }

    public static Directions of(Position startPosition, Position endPosition) {
        PositionDelta differentPosition = endPosition.minus(startPosition);
        return new Directions(calculateDirections(differentPosition.row(), differentPosition.column()));
    }

    public boolean checkAllDirectionIsStraight() {
        Direction standardDirection = directions.peek();
        if (!directions.isEmpty() && !standardDirection.isStraight()) {
            return false;
        }
        return directions.stream().allMatch(direction -> direction.isSameDirection(standardDirection));
    }

    public boolean hasNext() {
        return !directions.isEmpty();
    }

    public Direction next() {
        return directions.poll();
    }

    public boolean keepsDirection(Direction direction) {
        return directions.isEmpty() || directions.peek() == direction;
    }

    public int size() {
        return directions.size();
    }

    public Direction findFirst() {
        return directions.peek();
    }

    private static Queue<Direction> calculateDirections(int row, int column) {
        Queue<Direction> directions = new ArrayDeque<>();

        int rowAbs = Math.abs(row);
        int columnAbs = Math.abs(column);
        int rowSign = Integer.signum(row);
        int columnSign = Integer.signum(column);
        int diagonalCount = Math.min(rowAbs, columnAbs);
        int straightRowCount = rowAbs - diagonalCount;
        int straightColumnCount = columnAbs - diagonalCount;

        addStraightRowDirections(straightRowCount, directions, rowSign);
        addStraightColumnDirections(straightColumnCount, directions, columnSign);
        addDiagonalDirections(diagonalCount, directions, rowSign, columnSign);

        return directions;
    }

    private static void addStraightRowDirections(int straightRowCount, Queue<Direction> directions, int rowSign) {
        for (int i = 0; i < straightRowCount; i++) {
            directions.add(Direction.from(rowSign, 0));
        }
    }

    private static void addStraightColumnDirections(int straightColumnCount, Queue<Direction> directions, int columnSign) {
        for (int i = 0; i < straightColumnCount; i++) {
            directions.add(Direction.from(0, columnSign));
        }
    }

    private static void addDiagonalDirections(int diagonalCount, Queue<Direction> directions, int rowSign, int columnSign) {
        for (int i = 0; i < diagonalCount; i++) {
            directions.add(Direction.from(rowSign, columnSign));
        }
    }
}
