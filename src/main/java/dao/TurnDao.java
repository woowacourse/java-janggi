package dao;

import domain.piece.Team;

public interface TurnDao {

    Team load();

    void save(final Team turn);

    void remove();
}
