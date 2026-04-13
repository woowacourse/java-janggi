package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public class PalaceSlidingMoveRule implements MoveRule {

    private final Movement movement;

    public PalaceSlidingMoveRule(Movement movement) {
        this.movement = movement;
    }

    @Override
    public List<Position> execute(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        if (!boardMediator.isPalace(from)) {
            return List.of();
        }
        return movement.calculateTracesForPalace(from, teamType, boardMediator);
    }
}
