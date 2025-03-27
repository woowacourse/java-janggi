package janggi;

import janggi.board.Board;
import janggi.board.Position;
import janggi.board.strategy.NormalPlaceStrategy;
import janggi.exception.GameOverException;
import janggi.piece.Team;
import janggi.view.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final InputParser parser = new InputParser();

    public static void main(String[] args) {
        Board board = new Board(new NormalPlaceStrategy());
        Team attackTeam = Team.GREEN;
        outputView.printGameStartMessage(attackTeam);
        while (true) {
            outputView.printBoard(board);
            if (!playTurn(board, attackTeam)) {
                break;
            }
            attackTeam = attackTeam.convertTeam();
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
