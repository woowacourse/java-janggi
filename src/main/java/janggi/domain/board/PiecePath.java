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

    public int rowDifference() {
        return destination.rowValue() - source.rowValue();
    }

    public int columnDifference() {
        return destination.columnValue() - source.columnValue();
    }

    public boolean canReachToDestination(Movement movement) {
        if (!source.canMove(movement)) {
            return false;
        }
        Position moved = source.move(movement);
        return moved.equals(destination);
    }

    public List<Position> tracePositionsByDirection(Movement movement) {
        List<Position> positions = new ArrayList<>();

        Position current = source;

        for (Direction direction : movement.directions()) {
            current = current.move(Movement.from(direction));
            positions.add(current);
        }
        return positions;
    }

    public List<Position> getBetweenPositions() {
        Movement movement = Movement.from(calculateDirection());

        Position current = source.move(movement);
        List<Position> positions = new ArrayList<>();
        while (!current.equals(destination)) {
            positions.add(current);
            current = current.move(movement);
        }
        return positions;
    }

    public Direction calculateDirection() {
        int rowDir = getDirectionValue(rowDifference());
        int colDir = getDirectionValue(columnDifference());

        return Direction.from(rowDir, colDir);
    }

    private int getDirectionValue(int difference) {
        if (difference != 0) {
            return difference / Math.abs(difference);
        }
        return 0;
    }

    public boolean matchesMovementStep( int targetDistance1, int targetDistance2) {
        if(rowDifference() == targetDistance1 && columnDifference() == targetDistance2) {
            return true;
        }
        if(rowDifference() == targetDistance2 && columnDifference() == targetDistance1) {
            return true;
        }
        return false;
    }

    public boolean isInPalacePath() {
        return source.inPalace() && destination.inPalace();
    }

    public boolean isPalaceDiagonalLine() {
        return isInPalacePath() && isDiagonal() && hasPalaceCenter();
    }

    private boolean hasPalaceCenter() {
        List<Position> allPathPosition = new ArrayList<>(getBetweenPositions());
        allPathPosition.add(source);
        allPathPosition.add(destination);

        List<Position> centerPositions = List.of(Palace.CENTER_RED.getPosition(), Palace.CENTER_BLUE.getPosition());

        return allPathPosition.stream()
                .anyMatch(centerPositions::contains);
    }
}
