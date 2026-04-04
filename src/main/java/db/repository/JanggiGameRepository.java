package db.repository;

import core.JanggiGame;
import java.util.Optional;

public interface JanggiGameRepository {

    Long save(JanggiGame janggiGame);

    void update(Long gameId, JanggiGame janggiGame);

    Optional<JanggiGame> findById(Long gameId);
}
