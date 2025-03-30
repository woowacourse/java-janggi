package repository;

import domain.Game;
import java.util.List;

public interface GameRepository {
    void save(final Game game);

    boolean hasPlayingGame();

    List<String> findGameNameAll();
}
