package domain;

import java.util.List;

public class Soldier extends AbstractPiece {

    public Soldier(final Team team, final Score score) {
        super(team, score);
    }

    @Override
    public List<Point> getPossiblePoint(final Point prev, final Point newPoint) {
        return List.of();
    }

    @Override
    public boolean isMovable(final Distance distance) {
        if (Team.GREEN == team && distance.x() == 0 && distance.y() == 1) {
            return true;
        }
        if (Team.RED == team && distance.x() == 0 && distance.y() == -1) {
            return true;
        }
        return (distance.x() == 1 || distance.x() == -1) && distance.y() == 0;
    }
}
