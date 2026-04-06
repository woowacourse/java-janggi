package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.team.TeamType;

public interface BoardMediator {
    boolean hasPieceAt(Position position);

    boolean isCannon(Position position);

    boolean isSameTeamType(Position position, TeamType teamType);

    boolean isPalace(Position position);
}
