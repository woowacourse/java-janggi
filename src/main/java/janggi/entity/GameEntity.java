package janggi.entity;

import janggi.domain.game.Game;

public record GameEntity(
        String currentTurn
) {

    public static GameEntity from(Game game) {
        return new GameEntity(game.currentDynasty().name());
    }

}
