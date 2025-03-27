package domain.piece;

import domain.Position;
import domain.Team;
import domain.movestrategy.FixedMoveStrategy;
import java.util.List;

public class Pawn extends Piece {

    private FixedMoveStrategy moveStrategy;

    public Pawn(Team team, FixedMoveStrategy moveStrategy) {
        super(team);
        this.moveStrategy = moveStrategy;
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        return moveStrategy.calculatePath(startPosition, targetPosition, team);
    }
}
