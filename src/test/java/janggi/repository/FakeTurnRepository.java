package janggi.repository;

import janggi.database.entity.TurnEntity;
import janggi.domain.Team;
import janggi.domain.Turn;
import java.util.Optional;

public class FakeTurnRepository implements TurnRepository {

    private TurnEntity turnEntity = null;
    private Long autoIncrement = 1L;

    @Override
    public Long add(final Turn turn) {
        turnEntity = new TurnEntity(autoIncrement++, turn.getTurn().name());
        return turnEntity.getId();
    }

    @Override
    public Optional<Turn> findCurrent() {
        if (turnEntity == null) {
            return Optional.empty();
        }
        return Optional.of(new Turn(Team.valueOf(turnEntity.getTeam())));
    }

    @Override
    public void change(final Turn turn) {
        turnEntity = new TurnEntity(turnEntity.getId(), turn.getTurn().name());
    }

    @Override
    public void deleteCurrent() {
        turnEntity = null;
    }
}
