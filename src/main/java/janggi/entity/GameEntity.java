package janggi.entity;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.GameState;

public record GameEntity(
        String currentTurn,
        String gameState
) {

    public static GameEntity toEntity(Dynasty dynasty, GameState gameState) {
        return new GameEntity(dynasty.name(), gameState.name());
    }

}
