package repository;

import domain.Game;
import domain.position.Position;
import java.util.Optional;

public interface GameRepository {
    void save(Game game);

    void updateTurn(Game game, Position from, Position to);

    Optional<Game> findById(Long id);

    void deleteById(Long id);
}
