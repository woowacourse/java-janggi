package janggi.dao.entity;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Status;
import janggi.domain.side.Side;
import janggi.view.BoardSetUpFormat;

public record GameEntity(
        Integer id,
        String name,
        BoardSetUpFormat choSetUp,
        BoardSetUpFormat hanSetUp,
        Status status,
        Side winner
) {

    public static GameEntity of(String gameName, BoardSetUp choSetUp, BoardSetUp hanSetUp) {
        return new GameEntity(null, gameName, BoardSetUpFormat.from(choSetUp), BoardSetUpFormat.from(hanSetUp),
                Status.IN_PROGRESS, null);
    }
}
