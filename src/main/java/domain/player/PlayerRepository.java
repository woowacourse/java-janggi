package domain.player;

import java.sql.Connection;
import java.util.List;

public interface PlayerRepository {

    List<Player> findByGameId(Connection connection, long gameId);

    void saveAll(Connection connection, long gameId, List<Player> players);

    void updateScore(Connection connection, long gameId, Player player);
}
