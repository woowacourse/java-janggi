package janggi.dao.turn;

import janggi.dto.TurnDto;
import janggi.domain.players.Team;
import janggi.domain.players.Turn;

public class FakeTurnDao implements TurnDao {

    private TurnDto turnDto;

    public FakeTurnDao(final TurnDto turnDto) {
        this.turnDto = turnDto;
    }

    @Override
    public void initialize(final TurnDto givenTurnDto) {
        turnDto = givenTurnDto;
    }

    @Override
    public Turn selectCurrentTeam() {
        return new Turn(turnDto.team(), false, false);
    }

    @Override
    public void updateTurn(final Team team) {
        turnDto = new TurnDto(team);
    }

    @Override
    public void deleteAll() {
        turnDto = null;
    }
}
