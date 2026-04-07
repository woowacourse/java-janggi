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
    private Side currentTurn;

    public JanggiGame(Board board, Players players) {
        this.board = board;
        this.players = players;
        this.currentTurn = Side.CHO;
    }

    public boolean isFinished() {
        return board.isFinished();
    }

    public Side getWhoseTurn() {
        return currentTurn;
    }

    public void playGame(Movement movement) {
        board.move(movement.startPosition(), movement.endPosition(), currentTurn);
        players.updateState(board);
    }

    public void switchTurn() {
        this.currentTurn = currentTurn.next();
    }

    public Board getBoard() {
        return Board.of(board.getState());
    }

    public List<Player> getPlayers() {
        return players.getPlayers();
    }
}
