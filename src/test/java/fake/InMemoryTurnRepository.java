package fake;

import domain.turn.Turn;
import domain.turn.repository.TurnRepository;
import java.util.Optional;

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
    public Optional<Turn> findLast() {
        if (turn == null) {
            return Optional.empty();
        }

        return Optional.of(turn);
    }

    @Override
    public void deleteAll() {
        turn = null;
    }
}
