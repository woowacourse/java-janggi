package domain;

public class Elephant implements Piece {

    private final Team team;

    public Elephant(final Team team) {
        this.team = team;
    }

    @Override
    public boolean isMovable(final Distance distance) {
        int absoluteX = Math.abs(distance.x());
        int absoluteY = Math.abs(distance.y());
        if (absoluteX == 2 && absoluteY == 3) {
            return true;
        }
        return absoluteX == 3 && absoluteY == 2;
    }
}
