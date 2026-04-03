package janggi.entity;

import janggi.domain.game.Game;
import janggi.domain.side.Side;

public record GameEntity(
        Integer id,
        String name,
        String choSetUp,
        String hanSetUp,
        Status status,
        Side winner
) {

    public static GameEntity of(Game game, String choSetUp, String hanSetUp) {
        return new GameEntity(null, game.getName(), choSetUp, hanSetUp, Status.IN_PROGRESS, null);
    }
}
