package repository;

import domain.game.Game;
import java.util.List;

public interface GameRepository {
    void save(Game game, long id);

    Game findBy(long id);

    List<GameInformation> findAll();

    long count();
}
