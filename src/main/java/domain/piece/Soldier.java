package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Direction;
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
        final Direction direction = fromPoint.generateDirection(toPoint);
        if (Team.GREEN == team && direction.x() == 0 && direction.y() == 1) {
            return true;
        }
        if (Team.RED == team && direction.x() == 0 && direction.y() == -1) {
            return true;
        }
        return (direction.x() == 1 || direction.x() == -1) && direction.y() == 0;
    }

    @Override
    public PieceType type() {
        return PieceType.SOLDIER;
    }
}
