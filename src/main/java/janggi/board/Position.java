package janggi.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {

    private final int x;
    private final int y;
    private final boolean isPalace;
    private final List<Direction> candidateDirections;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.isPalace = filterPalace();
        this.candidateDirections = addPalaceDirections();
    }

    public Position move(Direction direction) {
        return new Position(x + direction.getDx(), y + direction.getDy());
    }

    public List<Position> movesTo(Direction... directions) {
        List<Position> candidates = new ArrayList<>();
        for (Direction direction : directions) {
            candidates.add(move(direction));
        }
        return candidates;
    }

    public List<Position> moveToCandidate() {
        List<Position> candidates = new ArrayList<>();
        for (Direction direction : candidateDirections) {
            candidates.add(new Position(x + direction.getDx(), y + direction.getDy()));
        }
        return candidates;
    }

    public boolean isOutOfRange(final int xLimit, final int yLimit) {
        return x < 0 || y < 0 || x > xLimit - 1 || y > yLimit - 1;
    }

    public boolean isPalace() {
        return isPalace;
    }

    public boolean isNotPalace() {
        return !isPalace;
    }

    private boolean filterPalace() {
        return x >= 3 && x <= 5 && !(y >= 3 && y <= 6);
    }

    private List<Direction> addPalaceDirections() {
        List<Direction> directions = new ArrayList<>(Direction.straightValues());
        if ((x == 3 && y == 9) || (x == 3 && y == 2)) {
            directions.add(Direction.UP_RIGHT);
            return directions;
        }
        if ((x == 3 && y == 7) || (x == 3 && y == 0)) {
            directions.add(Direction.DOWN_RIGHT);
            return directions;
        }
        if ((x == 4 && y == 8) || (x == 4 && y == 1)) {
            directions.addAll(Direction.diagonalValues());
            return directions;
        }
        if ((x == 5 && y == 9) || (x == 5 && y == 2)) {
            directions.add(Direction.UP_LEFT);
            return directions;
        }
        if ((x == 5 && y == 7) || (x == 5 && y == 0)) {
            directions.add(Direction.DOWN_LEFT);
            return directions;
        }
        return directions;
    }

    public List<Direction> getCandidateDirections() {
        return new ArrayList<>(candidateDirections);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
