package janggi.dao.turn;

import janggi.dto.TurnDto;
import janggi.domain.players.Team;
import janggi.domain.players.Turn;

public interface TurnDao {

    void initialize(TurnDto turnDto);

    Turn selectCurrentTeam();

    void updateTurn(Team team);

    void deleteAll();
}
