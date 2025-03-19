package domain;

public class Chariot extends AbstractPiece {

    public Chariot(final Team team) {
        super(team);
    }

    @Override
    public boolean isMovable(final Distance distance) {
        if (distance.x() == 0 && distance.y() != 0) {
            return true;
        }
        return distance.x() != 0 && distance.y() == 0;
    }
}
