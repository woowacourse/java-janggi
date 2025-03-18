package domain;

public class Chariot implements Piece {

    private final Team team;

    public Chariot(final Team team) {
        this.team = team;
    }

    @Override
    public boolean isMovable(final Distance distance) {
        if (distance.x() == 0 && distance.y() != 0) {
            return true;
        }
        return distance.x() != 0 && distance.y() == 0;
    }
}
