package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.game.BoardGame;
import janggi.domain.game.PlayingTurn;
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
        BoardGame boardGame = new BoardGame(new Board(repository.allPieces()), repository.getPlayingTurn());
        final PlayingTurn playingTurn = boardGame.playingTurn();

        retryingWhileBoolean(() -> {
            outputView.printBoard(boardGame.allPieces());
            UserInput userInput = inputView.readMoveOrder(playingTurn.currentTeam());

            if (userInput.wantsToQuit) {
                outputView.printScore(boardGame.scoreTeams());
                outputView.printSaveAndQuit();
                return false;
            }

            boardGame.movePiece(userInput.departure, userInput.arrival);
            repository.update(userInput.departure, userInput.arrival, playingTurn);
            return processAfterMoved(boardGame);
        });
    }

    private boolean processAfterMoved(final BoardGame boardGame) {
        if (boardGame.isGameOver()) {
            repository.clear();
            outputView.printScore(boardGame.scoreTeams());
            outputView.printFinished(boardGame.higherScoreTeam());
            return false;
        }
        return true;
    }

    private void retryingWhileBoolean(Supplier<Boolean> keepRunningSupplier) {
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
