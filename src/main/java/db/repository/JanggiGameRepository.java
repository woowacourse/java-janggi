package db.repository;

import core.JanggiGame;
import java.util.Optional;

public interface JanggiGameRepository {

    Optional<JanggiGame> findLatest();

    void saveLatest(JanggiGame game);
}
