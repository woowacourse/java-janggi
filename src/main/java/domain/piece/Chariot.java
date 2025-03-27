package domain.piece;

import domain.Position;
import domain.Team;
import domain.movestrategy.RangeMoveStrategy;
import java.util.List;

public class Chariot extends Piece {

    private RangeMoveStrategy moveStrategy;

    public Chariot(Team team, RangeMoveStrategy moveStrategy) {
        super(team);
        this.moveStrategy = moveStrategy;
    }

    public void setMoveStrategy(RangeMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }


    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        return moveStrategy.calculatePath(startPosition, targetPosition);
    }
}
