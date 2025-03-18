package domain;

public class Horse implements Piece {

    private final Team team;

    public Horse(final Team team) {
        this.team = team;
    }

    @Override
    public boolean isMovable(final Distance distance) {
        int absoluteX = Math.abs(distance.x());
        int absoluteY = Math.abs(distance.y());
        if (absoluteX == 1 && absoluteY == 2) {
            return true;
        }
        return absoluteX == 2 && absoluteY == 1;
    }
}
