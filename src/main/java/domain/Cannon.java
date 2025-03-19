package domain;

public class Cannon extends AbstractPiece {

    public Cannon(final Team team) {
        super(team);
    }

    @Override
    public boolean isMovable(final Distance distance) {
        final int absoluteX = Math.abs(distance.x());
        final int absoluteY = Math.abs(distance.y());
        if (absoluteX >= 2 && absoluteY == 0) {
            return true;
        }
        return absoluteX == 0 && absoluteY >= 2;
    }
}
