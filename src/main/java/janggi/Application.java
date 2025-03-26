package janggi;

import janggi.board.Board;
import janggi.board.BoardInitializer;
import janggi.board.GameOverException;
import janggi.board.position.Position;
import janggi.view.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final InputParser parser = new InputParser();

    public static void main(String[] args) {
        BoardInitializer boardInitializer = new BoardInitializer();
        Board board = boardInitializer.makeBoard();
        outputView.printGameStartMessage();
        Team team = Team.GREEN;
        while (true) {
            outputView.printBoard(board);
            if (!playTurn(board, team)) {
                break;
            }
            team = team.convertTeam();
        }
    }

    private static boolean playTurn(Board board, Team team) {
        try {
            String startAndGoal = inputView.readStartAndGoalPosition(team);
            Position startPosition = parser.splitStartPosition(startAndGoal);
            Position goalPosition = parser.splitGoalPosition(startAndGoal);
            board.movePiece(startPosition, goalPosition, team);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return playTurn(board, team);
        } catch (GameOverException e) {
            outputView.printBoard(board);
            outputView.printGameOver(team);
            return false;
        }
        return true;
    }
}
