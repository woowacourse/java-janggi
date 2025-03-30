package domain.turn.repository;

import domain.turn.Turn;

public interface TurnRepository {

    void save(final Turn turn);

    boolean exists();

    Turn findLast();

    void deleteAll();
}
