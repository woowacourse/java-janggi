package repository;

import application.GameSession;
import domain.game.JanggiGame;
import java.util.Optional;

public interface GameRepository {

    GameSession save(JanggiGame janggiGame);

    Optional<GameSession> findLatestRunningGame();

    void update(GameSession session);
}
