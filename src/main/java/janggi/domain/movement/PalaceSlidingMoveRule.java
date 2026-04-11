package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class PalaceSlidingMoveRule implements MoveRule {

    private final Movement movement;
    private final Direction direction;

    public PalaceSlidingMoveRule(Direction direction) {
        this.direction = direction;
        this.movement = new Movement(direction);
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        if (!boardMediator.isPalace(from)) {
            return List.of();
        }
        return movement.calculateTracesForPalace(from, teamType, boardMediator);
    }
}
