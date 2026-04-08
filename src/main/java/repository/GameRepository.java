package repository;

import dto.GameRowDetail;

import java.sql.Connection;
import java.util.Optional;

public interface GameRepository {

    void createTable(Connection connection);

    boolean existsGame(Connection connection);

    Optional<GameRowDetail> findOngoingGame(Connection connection);

    int save(Connection connection, String turn);

    void updateTurn(Connection connection, int gameId, String turn);

    void gameEnd(Connection connection, int gameId);
}
