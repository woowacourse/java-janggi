package janggi.dao.turn;

import janggi.dto.TurnDto;
import janggi.piece.players.Team;
import janggi.piece.players.Turn;

public interface TurnDao {

    void initialize(TurnDto turnDto);

    Turn selectCurrentTeam();

    void updateTurn(Team team);

    void deleteAll();
}
