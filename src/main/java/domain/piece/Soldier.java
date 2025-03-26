package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Distance;
import domain.position.Point;
import java.util.List;

public class Soldier extends AbstractPiece {

    public Soldier(final Team team) {
        super(team, Score.SOLDIER);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        return List.of();
    }

    @Override
    public boolean isMovable(final Point fromPoint, final Point toPoint) {
        final Distance distance = fromPoint.generateDistance(toPoint);
        if (Team.GREEN == team && distance.x() == 0 && distance.y() == 1) {
            return true;
        }
        if (Team.RED == team && distance.x() == 0 && distance.y() == -1) {
            return true;
        }
        return (distance.x() == 1 || distance.x() == -1) && distance.y() == 0;
    }

    @Override
    public PieceType type() {
        return PieceType.SOLDIER;
    }
}
