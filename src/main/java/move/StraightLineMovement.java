package move;

public class StraightLineMovement implements MoveStrategy {
    @Override
    public Position move(Position from, Position to) {
        if(from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException();
        }
        if(from.equals(to)) {
            throw new IllegalArgumentException();
        }
        return to;
    }
}
