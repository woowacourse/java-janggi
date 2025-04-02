package janggi;

import janggi.board.Board;
import janggi.piece.Pieces;
import janggi.player.Players;
import janggi.player.Score;
import janggi.player.Team;
import janggi.player.Turn;
import janggi.repository.dto.GameDto;

import java.time.LocalDateTime;

public class GameContext {

    private final GameId gameId;
    private final LocalDateTime startedAt;
    private final LocalDateTime lastSavedAt;
    private final Players players;
    private final Board board;
    private final Turn turn;

    private GameContext(final GameId gameId,
                        final LocalDateTime startedAt,
                        final LocalDateTime lastSavedAt,
                        final Players players,
                        final Board board,
                        final Turn turn) {
        this.gameId = gameId;
        this.startedAt = startedAt;
        this.lastSavedAt = lastSavedAt;
        this.players = players;
        this.board = board;
        this.turn = turn;
    }

    public static GameContext newGame(final Players players) {
        return new GameContext(
                GameId.unset(),
                LocalDateTime.now(),
                LocalDateTime.MIN,
                players,
                players.createBoard(),
                players.getTurn());
    }

    public static GameContext loadGame(final GameDto gameDto,
                                       final Players players) {
        return new GameContext(
                GameId.from(gameDto.id()),
                gameDto.startAt(),
                gameDto.lastSavedAt(),
                players,
                players.createBoard(),
                players.getTurn());
    }

    public GameContext update(final Players players) {
        return new GameContext(
                gameId,
                startedAt, lastSavedAt,
                players,
                players.createBoard(),
                players.getTurn());
    }

    public boolean isSaved() {
        return gameId.isSet();
    }

    public Score getScore(final Team team) {
        return players.getScore(team);
    }

    public GameId getGameId() {
        return gameId;
    }

    public Players getPlayers() {
        return players;
    }

    public Pieces getAlivePieces() {
        return board.getAlivePieces();
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }
}
