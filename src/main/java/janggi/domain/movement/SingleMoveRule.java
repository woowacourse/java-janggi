package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class SingleMoveRule implements MoveRule {

    private final Movement movementOrder;

    public SingleMoveRule(Movement movementOrder) {
        this.movementOrder = movementOrder;
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        return movementOrder.calculateTraces(from, teamType, boardMediator, 1);
    }
}
