package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.Formation;
import janggi.domain.piece.Piece;
import janggi.domain.player.Name;
import janggi.domain.player.Players;
import janggi.domain.space.Destinations;
import janggi.domain.space.Position;
import janggi.domain.state.ChoTurn;
import janggi.domain.state.GameState;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Game {
    private GameState gameState;
    private final Players players;
    private final List<MoveEvent> uncommittedEvents = new ArrayList<>();

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
        uncommittedEvents.add(new MoveEvent(source, target));
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

    public Formation getPlayerFormationBySide(Side side) {
        return players.getFormation(side);
    }

    public void forEachPiece(BiConsumer<Position, Piece> action) {
        gameState.getBoard().forEachPieces(action);
    }

    public List<MoveEvent> getUncommittedEvents() {
        return List.copyOf(uncommittedEvents);
    }

    public void clearEvents() {
        this.uncommittedEvents.clear();
    }
}
