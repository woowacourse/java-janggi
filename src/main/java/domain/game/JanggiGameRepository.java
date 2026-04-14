package domain.game;

import java.sql.Connection;
import java.util.Optional;

public interface JanggiGameRepository {

    long save(Connection connection);

    JanggiGame findById(Connection connection, long gameId);

    void update(Connection connection, JanggiGame game);

    Optional<Long> findLatestGameId(Connection connection);
}
