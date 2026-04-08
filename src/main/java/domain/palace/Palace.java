package domain.palace;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class Palace {
    private final int minRow;
    private final int maxRow;
    private final int minColumn;
    private final int maxColumn;
    private final Position center;

    public Palace(Position center) {
        this.center = center;
        this.minRow = center.row() - 1;
        this.maxRow = center.row() + 1;
        this.minColumn = center.column() - 1;
        this.maxColumn = center.column() + 1;
    }

    public boolean contains(Position position) {
        return position.row() >= minRow && position.row() <= maxRow &&
                position.column() >= minColumn && position.column() <= maxColumn;
    }

    public boolean isDiagonalLink(Position from, Position to) {
        if (!contains(from) || !contains(to)) {
            return false;
        }
        return (from.equals(center) && isCorner(to)) || (to.equals(center) && isCorner(from));
    }

    public List<Position> getStraightAdjacents(Position position) {
        if (!contains(position)) {
            return List.of();
        }

        List<Position> adjacents = new ArrayList<>();
        addIfContains(adjacents, position.goUp());
        addIfContains(adjacents, position.goDown());
        addIfContains(adjacents, position.goLeft());
        addIfContains(adjacents, position.goRight());

        return adjacents;
    }

    public List<Position> getDiagonalAdjacents(Position position) {
        if (!contains(position)) {
            return List.of();
        }

        List<Position> adjacents = new ArrayList<>();

        if (position.equals(center)) {
            Position up = position.goUp();
            addIfContains(adjacents, up.goLeft());
            addIfContains(adjacents, up.goRight());

            Position down = position.goDown();
            addIfContains(adjacents, down.goLeft());
            addIfContains(adjacents, down.goRight());
        } else if (isCorner(position)) {
            adjacents.add(center);
        }

        return adjacents;
    }

    public List<Position> getAllAdjacents(Position position) {
        if (!contains(position)) {
            return List.of();
        }

        List<Position> allAdjacents = new ArrayList<>(getStraightAdjacents(position));
        allAdjacents.addAll(getDiagonalAdjacents(position));
        return allAdjacents;
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