package infra.repository;

import controller.dto.MovedPieceRequest;
import domain.Game;
import java.util.List;

public interface GameRepository {
    void save(Game game);
    void saveMoveEvent(Game game, MovedPieceRequest request);
    List<String> findAllGameNames();
    Game findGameByName(String name);
}
