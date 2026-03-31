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

    public boolean isStraightDirection() {
        return !directions.isEmpty() && directions.peek().isStraight();
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
