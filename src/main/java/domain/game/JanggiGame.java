package domain.game;

import domain.board.Board;
import domain.piece.Team;
import domain.setup.Arrangements;
import domain.setup.Command;
import domain.setup.Coordinate;
import domain.state.GameState;
import domain.state.ReadyState;
import io.OutputView;
import java.util.Optional;

public class JanggiGame {
    private Turn turn;
    private GameState gameState;
    private Board board;

    public JanggiGame() {
        this.turn = new Turn(Team.HAN);
        this.gameState = new ReadyState(new Arrangements());
    }

    public JanggiGame(Turn turn, GameState gameState) {
        this.turn = turn;
        this.gameState = gameState;
    }

    public JanggiGame(Board board, Turn turn, GameState gameState) {
        this.board = board;
        this.turn = turn;
        this.gameState = gameState;
    }

    public void processCommand(Command command) {
        gameState = gameState.handle(this, command);
        nextTurn();
    }

    public void setupBoard(Arrangements arrangements) {
        this.board = Board.of(arrangements);
        this.turn = new Turn(Team.CHO);
    }

    public Optional<Board> getBoard() {
        return Optional.ofNullable(board);
    }

    public Turn getTurn() {
        return turn;
    }

    public Team getCurrentTeam() {
        return turn.team();
    }

    public Team getEnemy() {
        return turn.getEnemy();
    }

    public void move(Coordinate coordinate) {
        this.board = board.move(coordinate, turn);
    }

    public void nextTurn() {
        this.turn = turn.changeTeam();
    }

    public boolean isFinished() {
        return gameState.isFinished();
    }

    public void displayRequestCommand(OutputView outputView) {
        gameState.display(this, outputView);
    }

    public GameState getGameState() {
        return gameState;
    }
}
