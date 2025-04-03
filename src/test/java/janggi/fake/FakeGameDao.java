package janggi.fake;

import janggi.GameState;
import janggi.dao.entity.GameEntity;
import janggi.dao.game.GameDao;
import janggi.piece.Team;
import java.util.ArrayList;
import java.util.List;

public class FakeGameDao implements GameDao {

    private long id;
    private final List<GameEntity> gameEntities;

    public FakeGameDao(final GameEntity... gameEntities) {
        this.id = gameEntities.length;
        this.gameEntities = new ArrayList<>(List.of(gameEntities));
    }

    @Override
    public GameEntity findByStatus(final GameState gameStatus) {
        return gameEntities.stream()
                .filter(gameEntity -> gameEntity.getStatus() == gameStatus)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addGame() {
        final GameEntity gameEntity = new GameEntity(++id, Team.CHU, GameState.IN_PROGRESS, 72, 73.5);
        gameEntities.add(gameEntity);
    }

    @Override
    public void updateGameStatus(final Long gameId, final Team turnTeam, final double chuScore, final double hanScore) {

    }

    @Override
    public void deleteGameBy(final Long gameId) {

    }
}
