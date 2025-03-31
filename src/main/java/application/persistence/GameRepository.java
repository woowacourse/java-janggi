package application.persistence;

import domain.game.Game;
import java.util.List;

public interface GameRepository {

    Game findTurn();

    void updateTurn(Game current);

    void save(Game current);

    void delete();

    List<Game> findAll();
}
