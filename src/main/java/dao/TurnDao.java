package dao;

import domain.piece.Team;

import java.sql.Connection;

public interface TurnDao {

    Team load();

    void save(final Connection connection, final Team turn);

    void remove(final Connection connection);
}
