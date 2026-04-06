package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class PalaceMoveRule implements MoveRule {

    private final Movement movement;

    public PalaceMoveRule(Movement movementOrder) {
        this.movement = movementOrder;
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        if (!boardMediator.isPalace(from)) {
            return List.of();
        }
        return movement.calculateTracesOneForPalace(from, teamType, boardMediator);
    }
}