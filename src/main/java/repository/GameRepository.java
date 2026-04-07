package repository;

import dto.GameDto;

import java.sql.Connection;
import java.util.Optional;

public interface GameRepository {

    void createTable(Connection connection);

    boolean existsGame(Connection connection);

    Optional<GameDto> findOngoingGame(Connection connection);

    int save(Connection connection, String turn);

    void updateTurn(Connection connection, int gameId, String turn);

    void gameEnd(Connection connection, int gameId);
}
