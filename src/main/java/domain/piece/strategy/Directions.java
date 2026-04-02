package domain.piece.strategy;

import domain.board.Direction;
import domain.position.Position;

import java.util.Queue;

public class Directions {
    private final Queue<Direction> directions;

    private Directions(Queue<Direction> directions) {
        this.directions = directions;
    }

    public static Directions between(Position startPosition, Position endPosition) {
        return new Directions(Direction.of(startPosition, endPosition));
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
}
