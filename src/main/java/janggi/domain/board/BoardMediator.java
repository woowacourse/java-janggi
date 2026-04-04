package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.team.TeamType;

public interface BoardMediator {
    boolean hasPieceAt(Position position);

    boolean hasGeneral(TeamType teamType);

    boolean isCannon(Position position);

    boolean isSameTeamType(Position position, TeamType teamType);
}
