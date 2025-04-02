package janggi.repository;

import janggi.domain.Turn;
import java.util.Optional;

public interface TurnRepository {
    Long add(Turn turn);

    Optional<Turn> findCurrent();

    void change(Turn turn);

    void deleteCurrent();
}
