package domain.player;

import java.sql.Connection;
import java.util.List;

public interface PlayerRepository {

    List<Player> findByGameId(Connection connection, long gameId);

    List<Player> saveAll(Connection connection, long gameId, List<Player> players);

    void update(Connection connection, long gameId, Players players);
}
