package janggi.repository;

import janggi.domain.Turn;
import java.util.Optional;

public interface TurnRepository {
    Long add(Turn turn);

    Optional<Turn> find();

    void update(Turn turn);

    void delete();
}
