package domain.piece;

import domain.Position;
import domain.Team;
import domain.movestrategy.RangeMoveStrategy;
import java.util.List;

public class Cannon extends Piece {

    private RangeMoveStrategy moveStrategy;

    public Cannon(Team team, RangeMoveStrategy moveStrategy) {
        super(team);
        this.moveStrategy = moveStrategy;
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        return moveStrategy.calculatePath(startPosition, targetPosition);
    }

    @Override
    public boolean isCanon() {
        return true;
    }
}
