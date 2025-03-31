package application.persistence;

import domain.game.Game;
import java.util.List;

public interface GameRepository {

    void updateGame(Game game);

    Game save(Game game);

    void deleteGame(Game game);

    List<Game> findAll();
}
