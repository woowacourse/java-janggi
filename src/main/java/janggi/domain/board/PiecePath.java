package janggi.domain.board;

import java.util.ArrayList;
import java.util.List;

public class PiecePath {
    private final Position source;
    private final Position destination;

    public PiecePath(Position source, Position destination) {
        this.source = source;
        this.destination = destination;
    }

    public boolean isStraight() {
        return rowDifference() == 0 || columnDifference() == 0;
    }

    public boolean isDiagonal() {
        return Math.abs(rowDifference()) == Math.abs(columnDifference());
    }

    public boolean isInPalacePath() {
        return source.inPalace() && destination.inPalace();
    }

    public int rowDifference() {
        return destination.rowValue() - source.rowValue();
    }

    public int columnDifference() {
        return destination.columnValue() - source.columnValue();
    }

    public boolean canReachToDestination(Direction direction) {
        if(!source.canMove(direction)) {
            return false;
        }
        Position moved = source.move(direction);
        return moved.equals(destination);
    }

    public List<Position> tracePositionsByDirection(List<Direction> directions) {
        List<Position> positions = new ArrayList<>();

        Position current = source;
        for (Direction direction : directions) {
            current = current.move(direction);
            positions.add(current);
        }
        return positions;
    }

    public List<Position> getBetweenPositions() {
        Direction direction = calculateDirection();

        Position current = source.move(direction);
        List<Position> positions = new ArrayList<>();
        while(!current.equals(destination)) {
            positions.add(current);
            current = current.move(direction);
        }
        return positions;
    }

    public Direction calculateDirection() {
        int rowDir = getDirectionValue(rowDifference());
        int colDir = getDirectionValue(columnDifference());

        return Direction.from(rowDir,colDir);
    }

    private int getDirectionValue(int difference) {
        if(difference != 0) {
            return difference / Math.abs(difference);
        }
        return 0;
    }
}
