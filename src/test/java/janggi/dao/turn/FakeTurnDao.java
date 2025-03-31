package janggi.dao.turn;

import janggi.dto.TurnDto;
import janggi.piece.players.Team;
import janggi.piece.players.Turn;
import java.util.ArrayList;
import java.util.List;

public class FakeTurnDao implements TurnDao {

    private final List<TurnDto> dtos = new ArrayList<>();

    @Override
    public Turn selectCurrentTeam() {
        final TurnDto turnDto = dtos.stream()
                .filter(TurnDto::isCurrentTeam)
                .findAny()
                .orElseThrow(IllegalStateException::new);
        return new Turn(turnDto.team(), false, false);
    }

    @Override
    public void insert(final TurnDto turnDto) {
        dtos.add(turnDto);
    }

    @Override
    public void updateTurn(final Team team, final boolean isCurrentTeam) {
        final TurnDto currentTurn = dtos.stream()
                .filter(dto -> dto.team().equals(team))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        dtos.remove(currentTurn);
        dtos.add(new TurnDto(team, isCurrentTeam));
    }

    @Override
    public void deleteAll() {

    }
}
