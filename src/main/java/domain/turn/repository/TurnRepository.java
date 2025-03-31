package domain.turn.repository;

import domain.turn.Turn;
import java.util.Optional;

public interface TurnRepository {

    void save(final Turn turn);

    boolean exists();

    Optional<Turn> findLast();

    void deleteAll();
}
