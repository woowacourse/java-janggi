package janggi.model.movement.pattern;

import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import janggi.model.position.relative.RelativePosition;
import java.util.ArrayList;
import java.util.List;

public class MovementPattern {

    private static final int NEGATIVE_MULTIPLIER = -1;

    private final List<RelativePosition> relativePath;

    private MovementPattern(List<RelativePosition> relativePath) {
        this.relativePath = relativePath;
    }

    public static MovementPattern of(List<RelativePosition> positions) {
        return new MovementPattern(List.copyOf(positions));
    }

    public boolean isMatchedWith(Position from, Position to) {
        int totalRowOffset = relativePath.stream()
                .mapToInt(RelativePosition::rowOffset)
                .sum();

        int totalColumnOffset = relativePath.stream()
                .mapToInt(RelativePosition::columnOffset)
                .sum();

        return from.getRowDiff(to) == (totalRowOffset * NEGATIVE_MULTIPLIER)
                &&from.getColumnDiff(to) == (totalColumnOffset * NEGATIVE_MULTIPLIER);
    }

    public PositionPath createPath(Position from) {
        Position current = from;
        List<Position> positions = new ArrayList<>();

        for (RelativePosition relativePosition : relativePath) {
            current = relativePosition.moved(current);
            positions.add(current);
        }

        positions.removeLast();

        return new PositionPath(positions);
    }
}
