package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.player.Name;
import janggi.domain.player.Players;
import janggi.domain.space.Destinations;
import janggi.domain.space.Position;
import janggi.domain.state.ChoTurn;
import janggi.domain.state.GameState;
import java.util.Map;

public class Game {
    private GameState gameState;
    private final Players players;

    private Game(GameState gameState, Players players) {
        this.gameState = gameState;
        this.players = players;
    }

    public static Game startNew(Board board, Players players) {
        return new Game(new ChoTurn(board), players);
    }

    public static Game restore(GameState gameState, Players players) {
        return new Game(gameState, players);
    }

    public Destinations selectSource(Position source) {
        return gameState.selectSource(source);
    }

    public void move(Position source, Position target) {
        this.gameState = gameState.move(source, target);
    }

    public boolean isPlaying() {
        return gameState.isPlaying();
    }

    public Score calculateScore(Side side) {
        return gameState.calculateScore(side);
    }

    public Name getWinner() {
        Side winnerSide = gameState.getWinnerSide();
        return players.getNameBySide(winnerSide);
    }

    public Side getCurrentSide() {
        return gameState.getCurrentSide();
    }

    public Name getPlayerNameBySide(Side side) {
        return players.getNameBySide(side);
    }

    public Map<Position, Piece> getBoard() {
        return gameState.getBoard().getBoard();
    }
}
