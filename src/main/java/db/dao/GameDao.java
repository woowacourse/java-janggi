package db.dao;

import db.model.Game;
import java.util.Optional;

public interface GameDao {

    Long save(Game gameRecord);

    void update(Game gameRecord);

    Optional<Game> findById(Long id);
}
