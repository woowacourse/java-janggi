package repository.dao;

import domain.GameContext;
import domain.GameId;
import domain.JanggiGame;
import java.sql.Connection;
import java.util.List;

public interface GameDao {
    void initTable(Connection connection);

    GameId save(Connection connection, JanggiGame game);

    JanggiGame findGameById(Connection connection, GameId entityId);

    List<GameId> findPlayingGameIds(Connection connection);

    List<JanggiGame> findPlayingGames(Connection connection);

    void updateContext(Connection connection, GameId entityId, GameContext newEntity);

    void updateGamePiece(Connection connection, GameId id, JanggiGame game);
}
