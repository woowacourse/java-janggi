package db.dao;

import db.model.Game;
import java.util.Optional;

public interface GameDao {

    Long save(Game game);

    void update(Game game);

    Optional<Game> findById(Long id);

    Optional<Game> findLatest();
}
