package janggi;

import janggi.board.Board;
import janggi.piece.Pieces;
import janggi.player.Player;
import janggi.player.Players;
import janggi.player.Score;
import janggi.player.Team;
import janggi.player.Turn;
import janggi.repository.dto.GameDto;

public class GameContext {

    private final GameId gameId;
    private final Players players;
    private final Board board;

    private GameContext(final GameId gameId,
                        final Players players,
                        final Board board) {
        this.gameId = gameId;
        this.players = players;
        this.board = board;
    }

    public static GameContext newGame(final Players players) {
        return new GameContext(
                GameId.unset(),
                players,
                players.createBoard());
    }

    public static GameContext loadGame(final GameDto gameDto,
                                       final Players players) {
        return new GameContext(
                GameId.from(gameDto.id()),
                players,
                players.createBoard());
    }

    public boolean isSaved() {
        return gameId.isSet();
    }

    public void nextTurn() {
        getTurn().next();
    }

    public Player getCurrentPlayer() {
        return players.getCurrentPlayer();
    }

    public Score getScore(final Team team) {
        return players.getScore(team);
    }

    public GameId getGameId() {
        return gameId;
    }

    public Pieces getAlivePieces() {
        return board.getAlivePieces();
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return players.getTurn();
    }
}
