package janggi.infra.repository.turn_repository;

import janggi.domain.Country;

public interface TurnRepository {

    void createTable();

    void deleteTable();

    Country findNextTurn(int number);

    void saveTurn(int number, Country country);

}
