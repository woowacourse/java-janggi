package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class PalaceMoveRule implements MoveRule {

    private final Movement movement;
    private final Direction direction;

    public PalaceMoveRule(Direction direction) {
        this.direction = direction;
        this.movement = new Movement(direction);
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        if (!Palace.isAllowedDirection(from, direction)) {
            return List.of();
        }
        return movement.calculateTracesOne(from, teamType, boardMediator);
    }
}