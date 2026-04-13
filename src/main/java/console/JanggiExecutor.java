package console;

import static console.util.ExceptionHandler.handleRetry;

import application.JanggiService;
import application.MoveCommand;
import console.view.InputView;
import console.view.OutputView;
import model.JanggiGame;
import model.Team;
import model.coordinate.Position;
import model.piece.Piece;

public class JanggiExecutor {

    private final JanggiService janggiService;

    public JanggiExecutor(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    public void playGame(Long janggiId) {
        JanggiGame janggiGame = janggiService.getGame(janggiId);

        while (janggiGame.canPlaying()) {
            playOneTurn(janggiId, janggiGame);
            janggiGame = janggiService.getGame(janggiId);
        }
        printResult(janggiId);
    }

    private void playOneTurn(Long janggiId, JanggiGame currentGame) {
        OutputView.displayBoard(currentGame.getBoard());
        handleRetry(() -> checkBigJang(janggiId, currentGame), OutputView::displayError);
        playAfterBigJangCheck(janggiId);
    }

    private void playAfterBigJangCheck(Long janggiId) {
        JanggiGame janggiGame = janggiService.getGame(janggiId);
        if (janggiGame.canPlaying()) {
            handleRetry(() -> playToMovePiece(janggiId, janggiGame), OutputView::displayError);
        }
    }

    private void checkBigJang(Long janggiId, JanggiGame janggiGame) {
        if (!janggiGame.isBigJang()) {
            return;
        }

        boolean bigJang = InputView.readBigJangStatus(janggiGame.turn());
        if (bigJang) {
            janggiService.finishByBigJang(janggiId);
        }
    }

    private void playToMovePiece(Long janggiId, JanggiGame janggiGame) {
        Team currentTurn = janggiGame.turn();

        Position current = InputView.readPiecePositionForMove(currentTurn);
        Piece piece = janggiGame.selectPiece(current);

        Position next = InputView.readPiecePositionForArrange(currentTurn, piece);
        janggiService.movePiece(janggiId, new MoveCommand(current, next));
    }

    private void printResult(Long janggiId) {
        JanggiGame janggiGame = janggiService.getGame(janggiId);
        OutputView.displayWinner(janggiGame.resolveWinner());

        if (janggiGame.isBigJangDone()) {
            OutputView.displayScore(janggiGame.calculateFinalScore());
        }
    }
}
