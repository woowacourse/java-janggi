package infra.repository;

import domain.turn.Turn;
import domain.turn.repository.TurnRepository;

public class InMemoryTurnRepository implements TurnRepository {

    private Turn turn;

    @Override
    public void save(final Turn turn) {
        this.turn = turn;
    }

    @Override
    public boolean exists() {
        return turn != null;
    }

    @Override
    public Turn findLast() {
        return turn;
    }

    @Override
    public void deleteAll() {
        turn = null;
    }
}
