package dao;

import domain.Game;
import java.util.List;

public interface GameDao {
    void save(final Game game);

    boolean hasPlayingGame();

    List<String> findGameNameAll();
}
