package domain;

public class Soldier implements Piece {

    private final Team team;

    public Soldier(final Team team) {
        this.team = team;
    }

    @Override
    public boolean isMovable(final Distance distance) {
        if (distance.x() == 0 && distance.y() == 1) {
            return true;
        }
        return (distance.x() == 1 || distance.x() == -1) && distance.y() == 0;
    }
}
