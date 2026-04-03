package janggi.entity;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.side.Side;

public record GameEntity(
        Integer id,
        String name,
        SetUpEntity choSetUp,
        SetUpEntity hanSetUp,
        Status status,
        Side winner
) {

    public static GameEntity of(String gameName, BoardSetUp choSetUp, BoardSetUp hanSetUp) {
        return new GameEntity(null, gameName, SetUpEntity.from(choSetUp), SetUpEntity.from(hanSetUp),
                Status.IN_PROGRESS, null);
    }
}
