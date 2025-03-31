package janggi.dao.turn;

import janggi.dto.TurnDto;
import janggi.piece.players.Team;
import janggi.piece.players.Turn;

public interface TurnDao {

    Turn selectCurrentTeam();

    void insert(TurnDto turnDto);

    void updateTurn(Team team, boolean isCurrentTeam);

    void deleteAll();
}
