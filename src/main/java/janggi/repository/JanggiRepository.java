package janggi.repository;

import janggi.domain.JanggiGame;
import java.sql.Connection;
import java.util.Optional;

public interface JanggiRepository {
    JanggiGame save(Connection conn, JanggiGame game);

    Optional<JanggiGame> findInProgressGame(Connection conn);

    void update(Connection conn, JanggiGame game);
}
