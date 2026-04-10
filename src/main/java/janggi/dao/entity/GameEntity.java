package janggi.dao.entity;

import janggi.domain.board.Board;
import janggi.domain.game.Game;
import janggi.domain.game.Status;
import janggi.domain.side.Side;

public record GameEntity(
        Integer id,
        String name,
        Side turn,
        Status status,
        Side winner
) {

    public static GameEntity fromDomain(Game game) {
        return new GameEntity(game.getId(), game.getName(), game.getTurn(), game.getStatus(), game.getWinner());
    }

    public Game toDomain(Board board) {
        return new Game(id, name, board, status, turn, winner);
    }
}
