package janggi.domain;

import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;

public class GameManager {

    public boolean isGeneralAlive(TeamType teamType, BoardMediator boardMediator) {
        return boardMediator.hasGeneral(teamType);
    }
}
