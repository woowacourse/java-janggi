package domain.palace;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class Palace {
    private final Position topLeft;
    private final Position bottomRight;

    public Palace(Position center) {
        this.topLeft = center.goUpLeft();
        this.bottomRight = center.goDownRight();
    }

    public boolean contains(Position position) {
        return position.isWithin(topLeft, bottomRight);
    }

    public boolean isDiagonalLink(Position from, Position to) {
        if (!contains(from) || !contains(to)) {
            return false;
        }
        Position palaceCenter = center();
        return (from.equals(palaceCenter) && isCorner(to))
                || (to.equals(palaceCenter) && isCorner(from));
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
        if (position.equals(center())) {
            return centerDiagonalNeighbors(position);
        }
        if (isCorner(position)) {
            return List.of(center());
        }
        return List.of();
    }

    public List<Position> getAllAdjacents(Position position) {
        if (!contains(position)) {
            return List.of();
        }

        List<Position> allAdjacents = new ArrayList<>(getStraightAdjacents(position));
        allAdjacents.addAll(getDiagonalAdjacents(position));
        return allAdjacents;
    }

    private Position center() {
        return Position.centerOf(topLeft, bottomRight);
    }

    private boolean isCorner(Position position) {
        Position palaceCenter = center();
        return contains(position) && !position.equals(palaceCenter)
                && !position.sharesRowOrColumnWith(palaceCenter);
    }

    private List<Position> centerDiagonalNeighbors(Position position) {
        List<Position> adjacents = new ArrayList<>();
        Position up = position.goUp();
        addIfContains(adjacents, up.goLeft());
        addIfContains(adjacents, up.goRight());
        Position down = position.goDown();
        addIfContains(adjacents, down.goLeft());
        addIfContains(adjacents, down.goRight());
        return adjacents;
    }

    private void addIfContains(List<Position> list, Position position) {
        if (contains(position)) {
            list.add(position);
        }
    }
}
