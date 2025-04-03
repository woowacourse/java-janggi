package janggi.view;

import janggi.board.Board;
import janggi.board.Position;
import janggi.piece.Team;

public class Console {
    private final InputView inputView;
    private final OutputView outputView;
    private final InputParser parser;

    public Console(InputView inputView, OutputView outputView, InputParser parser) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.parser = parser;
    }

    public String readStartAndGoalPosition(Team team) {
        return inputView.readStartAndGoalPosition(team);
    }

    public void printGameStartMessage(Team team) {
        outputView.printGameStartMessage(team);
    }

    public void printGameOver(Team winningTeam) {
        outputView.printGameOver(winningTeam);
    }

    public void printBoard(Board currentBoard) {
        outputView.printBoard(currentBoard);
    }

    public void printErrorMessage(IllegalArgumentException e) {
        outputView.printErrorMessage(e);
    }

    public void printGameScore(Board board) {
        outputView.printGameScore(board);
    }

    public Position splitStartPosition(String input) {
        return parser.splitStartPosition(input);
    }

    public Position splitGoalPosition(String input) {
        return parser.splitGoalPosition(input);
    }
}
