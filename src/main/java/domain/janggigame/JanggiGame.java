package domain.janggigame;

import domain.board.Board;
import domain.piece.Side;
import domain.players.Player;
import domain.players.Players;
import domain.position.Movement;

import java.util.List;

public class JanggiGame {
    private final Board board;
    private final Players players;

    public JanggiGame(Board board, Players players) {
        this.board = board;
        this.players = players;
    }

    public boolean isFinished() {
        return board.isFinished();
    }

    public Side getWhoseTurn() {
        return players.getWhoseTurn();
    }

    public void playGame(Movement movement) {
        board.move(movement.startPosition(), movement.endPosition(), players.getWhoseTurn());
        players.updateState(board);
    }

    public void switchTurn() {
        players.switchTurn();
    }

    public Board getBoard() {
        return Board.of(board.getState());
    }

    public List<Player> getPlayers() {
        return players.getPlayers();
    }
}
