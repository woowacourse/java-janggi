package infra.repository;

import domain.Game;
import java.util.List;

public interface GameRepository {
    void save(Game game);
    List<String> findAllGameNames();
    Game findGameByName(String name);
}
