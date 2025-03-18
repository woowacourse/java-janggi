package move;


public class CastleMovement implements MoveStrategy {
    private static final int MAXIMUM_MOVEMENT_LIMIT = 1;

    @Override
    public Position move(Position from, Position to) {
        validateMoveRange(from, to);
        return to;
    }

    private void validateMoveRange(Position from, Position to) {
        validateHorizontalRange(from.x(), to.x());
        validateVerticalRange(from.y(), to.y());
    }

    private void validateHorizontalRange(int fromX, int toX) {
        if(fromX - MAXIMUM_MOVEMENT_LIMIT > toX || fromX + MAXIMUM_MOVEMENT_LIMIT < toX) {
            throw new IllegalArgumentException();
        }
    }

    private void validateVerticalRange(int fromY, int toY) {
        if (fromY + MAXIMUM_MOVEMENT_LIMIT < toY || fromY - MAXIMUM_MOVEMENT_LIMIT > toY) {
            throw new IllegalArgumentException();
        }
    }
}
