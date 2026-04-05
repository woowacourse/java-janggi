package repository;

import dto.GameDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface GameRepository {

    void createTable(Connection connection) throws SQLException;

    boolean existsGame(Connection connection) throws SQLException;

    Optional<GameDto> findOngoingGame(Connection connection) throws SQLException;

    int save(Connection connection, String turn) throws SQLException;
}
