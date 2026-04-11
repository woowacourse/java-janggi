package domain.game;

import domain.board.Board;
import domain.piece.Team;
import domain.setup.Arrangements;
import domain.setup.Command;
import domain.setup.Coordinate;
import domain.state.GamePhase;
import domain.state.GameState;
import domain.state.ReadyState;

public class JanggiGame {
    private Turn turn;
    private GameState gameState;
    private Board board;

    public JanggiGame() {
        this.turn = new Turn(Team.HAN);
        this.gameState = new ReadyState(new Arrangements());
    }

    public void processCommand(Command command) {
        gameState = gameState.handle(this, command);
    }

    public void setupBoard(Arrangements arrangements) {
        this.board = Board.of(arrangements);
        this.turn = new Turn(Team.CHO);
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }

    public void move(Coordinate coordinate) {
        this.board = board.move(coordinate, turn.getTeam());
    }

    public void nextTurn() {
        this.turn = turn.changeTeam();
    }

    public boolean isReadyPhase() {
        return gameState.phase() == GamePhase.READY;
    }

    public boolean isPlayingPhase() {
        return gameState.phase() == GamePhase.PLAYING;
    }

}
