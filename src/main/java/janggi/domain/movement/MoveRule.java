package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public interface MoveRule {
    List<Position> execute(Position from, TeamType teamType, BoardMediator boardMediator);
}
