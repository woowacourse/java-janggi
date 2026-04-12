package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class PalaceMoveRule implements MoveRule {

    private final Movement movement;

    public PalaceMoveRule(Direction direction) {
        this.movement = new Movement(direction);
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        if (!movement.isAllowedInPalace(from)) {
            return List.of();
        }
        return movement.calculateTraces(from, teamType, boardMediator, 1);
    }
}