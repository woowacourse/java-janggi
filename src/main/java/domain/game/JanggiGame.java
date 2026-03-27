package domain.game;

import domain.command.Command;
import domain.state.GameState;
import domain.state.ReadyState;
import domain.vo.Arrangements;
import domain.vo.Coordinate;
import domain.vo.Team;
import io.OutputView;
import java.util.EnumMap;

public class JanggiGame {
    private Turn turn;
    private GameState gameState;
    private Board board;

    public void setupBoard(Arrangements arrangements) {
        this.board = Board.of(arrangements);
        this.turn = new Turn(Team.CHO);
        this.gameState = new ReadyState(new EnumMap<>(Team.class));
    }

    public void processCommand(Command command) {
        gameState = gameState.handle(this, command);
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

    public void displayRequestCommand(OutputView outputView) {
        gameState.display(this, outputView);
    }
}
