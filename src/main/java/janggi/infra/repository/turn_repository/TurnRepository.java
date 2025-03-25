package janggi.infra.repository.turn_repository;

import janggi.domain.Country;

public interface TurnRepository {

    void createTable();

    void deleteTable();

    Country findNextTurn(final int number);

    void saveTurn(final int number, final Country country);

}
