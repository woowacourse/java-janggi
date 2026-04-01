package domain.palace;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class Palace {

    private static final int MIN_COLUMN = 3;
    private static final int MAX_COLUMN = 5;

    private final int minRow;
    private final int maxRow;
    private final Position center;

    private Palace(int minRow, int maxRow, Position center) {
        this.minRow = minRow;
        this.maxRow = maxRow;
        this.center = center;
    }

    public static Palace createHanPalace() {
        return new Palace(0, 2, Position.of(1, 4));
    }

    public static Palace createChoPalace() {
        return new Palace(7, 9, Position.of(8, 4));
    }

    public boolean contains(Position position) {
        return position.row() >= minRow && position.row() <= maxRow &&
                position.column() >= MIN_COLUMN && position.column() <= MAX_COLUMN;
    }

    public boolean isDiagonalLink(Position from, Position to) {
        if (!contains(from) || !contains(to)) {
            return false;
        }
        return (from.equals(center) && isCorner(to)) || (to.equals(center) && isCorner(from));
    }

    public List<Position> getAdjacentPositions(Position position) {
        if (!contains(position)) {
            return List.of();
        }

        List<Position> adjacents = new ArrayList<>();


        addIfContains(adjacents, Position.of(position.row() - 1, position.column()));
        addIfContains(adjacents, Position.of(position.row() + 1, position.column()));
        addIfContains(adjacents, Position.of(position.row(), position.column() - 1));
        addIfContains(adjacents, Position.of(position.row(), position.column() + 1));


        if (position.equals(center)) {
            addIfContains(adjacents, Position.of(position.row() - 1, position.column() - 1));
            addIfContains(adjacents, Position.of(position.row() - 1, position.column() + 1));
            addIfContains(adjacents, Position.of(position.row() + 1, position.column() - 1));
            addIfContains(adjacents, Position.of(position.row() + 1, position.column() + 1));
            return adjacents;
        }

        if (isCorner(position)) {
            adjacents.add(center);
        }

        return adjacents;
    }

    private boolean isCorner(Position position) {
        return contains(position) && !position.equals(center) &&
                position.row() != center.row() && position.column() != center.column();
    }

    private void addIfContains(List<Position> list, Position position) {
        if (contains(position)) {
            list.add(position);
        }
    }
}