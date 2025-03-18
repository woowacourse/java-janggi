package domain;

public class Cannon implements Piece {

    private final Team team;

    public Cannon(final Team team) {
        this.team = team;
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
