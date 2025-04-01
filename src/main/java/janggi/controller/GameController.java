package janggi.controller;

import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGame;
import janggi.domain.board.PlayingTurn;
import janggi.repository.Repository;
import janggi.view.InputView;
import janggi.view.InputView.UserInput;
import janggi.view.OutputView;
import java.util.function.Supplier;

public class GameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Repository repository;

    public GameController(final InputView inputView, final OutputView outputView,
        final Repository repository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.repository = repository;
    }

    public void play() {
        BoardGame boardGame = new BoardGame(new Board(repository.allPieces()), repository.getTurn());
        retryingWhileCondition(() -> {
            outputView.printBoard(boardGame.allPieces());

            Team currentTurn = boardGame.currentTeam();
            UserInput userInput = inputView.readMoveOrder(currentTurn);
            if (userInput.wantsToQuit) {
                outputView.printScore(boardGame.scoreTeams());
                outputView.printSaveAndQuit();
                return false;
            }

            boardGame.movePiece(userInput.departure, userInput.arrival);
            repository.update(userInput.departure, userInput.arrival);
            repository.updateTurn(new PlayingTurn(boardGame.currentTeam(), boardGame.currentRound()));

            if (boardGame.isGameOver()) {
                repository.clear();
                repository.updateTurn(new PlayingTurn());
                outputView.printScore(boardGame.scoreTeams());
                outputView.printFinished(boardGame.higherScoreTeam());
                return false;
            }
            return true;
        });
    }

    private void retryingWhileCondition(Supplier<Boolean> keepRunningSupplier) {
        boolean keepRunning = true;
        do {
            try {
                keepRunning = keepRunningSupplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printException(e);
            }
        } while (keepRunning);
    }
}
