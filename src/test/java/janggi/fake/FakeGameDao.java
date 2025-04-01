package janggi.fake;

import janggi.dao.GameDao;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.Status;
import janggi.domain.piece.Dynasty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakeGameDao implements GameDao {

    private long id;
    private final List<GameEntity> gameEntities;

    public FakeGameDao(GameEntity... gameEntities) {
        this.gameEntities = new ArrayList<>(List.of(gameEntities));
        id = gameEntities.length;
    }

    @Override
    public Optional<GameEntity> findById(Long gameId) {
        return gameEntities.stream()
                .filter(gameEntity -> Objects.equals(gameEntity.getId(), gameId))
                .findFirst();
    }

    @Override
    public void addGame(GameEntity gameEntity) {
        gameEntity.setId(++id);
        gameEntities.add(gameEntity);
    }

    @Override
    public void updateCurrentTurn(Long gameId, Dynasty currentTurn) {
        GameEntity gameEntity = findById(gameId).orElseThrow(IllegalArgumentException::new);
        gameEntity.setCurrentTurn(currentTurn);
    }

    @Override
    public void updateStatus(Long gameId, Status status) {
        GameEntity gameEntity = findById(gameId).orElseThrow(IllegalArgumentException::new);
        gameEntity.setStatus(status);
    }

    @Override
    public Optional<GameEntity> findByName(String name) {
        return gameEntities.stream()
                .filter(gameEntity -> gameEntity.getName().equals(name))
                .findFirst();
    }
}
