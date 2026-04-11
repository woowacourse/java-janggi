package infra.repository;

import domain.Game;
import java.util.Optional;

public interface GameRepository {
    void save(Game game);
}
