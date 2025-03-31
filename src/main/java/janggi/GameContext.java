package janggi;

import janggi.board.Board;
import janggi.player.Players;
import janggi.player.Turn;

import java.time.LocalDateTime;

public class GameContext {

    private Long gameId;
    private final LocalDateTime startAt;
    private final Players players;
    private final Board board;
    private final Turn turn;

    public GameContext(final Long gameId,
                       final LocalDateTime startAt,
                       final Players players,
                       final Board board,
                       final Turn turn) {
        this.gameId = gameId;
        this.startAt = startAt;
        this.players = players;
        this.board = board;
        this.turn = turn;
    }

    public void setGameId(final long gameId) {
        this.gameId = gameId;
    }

    public Long getGameId() {
        return gameId;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public Players getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }
}
