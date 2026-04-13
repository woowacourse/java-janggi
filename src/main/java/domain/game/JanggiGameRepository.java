package domain.game;

import java.sql.Connection;

public interface JanggiGameRepository {

    long save(Connection connection);

    JanggiGame findById(Connection connection, long gameId);

    void update(Connection connection, JanggiGame game);
}
