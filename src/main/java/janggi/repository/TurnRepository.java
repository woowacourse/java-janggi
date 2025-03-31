package janggi.repository;

import janggi.domain.Turn;

public interface TurnRepository {
    Long add(Turn turn);

    Turn find();

    void update(Turn turn);

    void delete();
}
