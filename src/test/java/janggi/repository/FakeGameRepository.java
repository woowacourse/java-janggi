package janggi.repository;

import java.util.Optional;

import janggi.domain.game.Game;
import janggi.domain.side.Side;

public class FakeGameRepository implements GameRepository {

    private Game game;
    private int gameId;
    private boolean isFinish = false;

    @Override
    public Optional<Integer> findActiveGameId() {
        if (game == null || isFinish) {
            return Optional.empty();
        }
        return Optional.of(gameId);
    }

    @Override
    public int save(Game game) {
        this.game = game;
        this.gameId = 1;
        this.isFinish = false;
        return gameId;
    }

    @Override
    public void update(int gameId, Game game) {
        this.game = game;
    }

    @Override
    public Game load(int gameId) {
        return game;
    }

    @Override
    public void finish(int gameId, Side winner) {
        this.isFinish = true;
    }
}
