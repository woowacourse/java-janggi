package repository;

import domain.Game;
import java.util.Optional;

public interface GameRepository {
    void save(Game game);

    Optional<Game> findById(Long id);

    void deleteById(Long id);
}
