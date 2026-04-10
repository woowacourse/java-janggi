package repository;

import domain.game.Game;
import java.util.Optional;

public class InMemoryGameRepository implements GameRepository {
    private Game game;

    @Override
    public void save(Game game) {
        this.game = game;
    }

    @Override
    public Optional<Game> findInProgressGame() {
        if (game == null || game.isFinished()) {
            return Optional.empty();
        }

        return Optional.of(game);
    }
}
