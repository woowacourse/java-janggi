package domain;

public class General implements Piece {

    private final Team team;

    public General(final Team team) {
        this.team = team;
    }

    @Override
    public boolean isMovable(final Distance distance) {
        final int absoluteX = Math.abs(distance.x());
        final int absoluteY = Math.abs(distance.y());
        if (absoluteX == 1 && absoluteY == 0) {
            return true;
        }
        return absoluteX == 0 && absoluteY == 1;
    }
}
