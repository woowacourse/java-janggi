package janggi.service;

import janggi.domain.turn.TurnManager;
import janggi.entity.GameStateEntity;
import janggi.repository.GameStateRepository;

public class GameStateService {

    private final GameStateRepository gameStateRepository;

    public GameStateService(final GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    public long createGameState(final TurnManager turnManager) {
        final GameStateEntity gameStateEntity = GameStateEntity.from(turnManager);

        return gameStateRepository.save(gameStateEntity);
    }

    public long modifyGameState(final long id, final TurnManager turnManager) {
        final GameStateEntity gameStateEntity = GameStateEntity.from(id, turnManager);

        return gameStateRepository.update(gameStateEntity);
    }
}
