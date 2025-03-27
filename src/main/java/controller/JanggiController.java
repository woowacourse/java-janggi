package controller;

import domain.janggiboard.JanggiBoard;
import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import domain.position.JanggiPosition;
import domain.piece.JanggiSide;
import java.util.List;
import service.JanggiService;
import view.GameContinueOption;
import view.InputView;
import view.OutputView;

public class JanggiController {

    public static final JanggiSide JANGGI_GAME_STARTING_SIDE = JanggiSide.CHO;
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(
            final InputView inputView,
            final OutputView outputView,
            final JanggiService janggiService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        JanggiBoard board = setJanggiBoard();
        outputView.printBoard(board.getBoard());
        JanggiSide nowTurn = JANGGI_GAME_STARTING_SIDE;

        while (true) {
            processMovePiece(board, nowTurn);
            outputView.printBoard(board.getBoard());
            if (janggiService.isGameEnd(board, nowTurn)) {
                break;
            }
            nowTurn = nowTurn.getOppositeSide();
        }

        printResult(nowTurn, board);
        janggiService.finishGame();
    }

    private JanggiBoard setJanggiBoard() {
        if (janggiService.isPreviousGameNotOver()) {
            return loadBoardWhenPreviousGameExist();
        }
        return createNewBoard();
    }

    private JanggiBoard loadBoardWhenPreviousGameExist() {
        GameContinueOption continueSelection = InputProcessor.repeatUntilNormalInput(
                inputView::getPreviousGameContinueSelectionInput,
                OutputView::printErrorMessage
        );
        if (continueSelection == GameContinueOption.Y) {
            return janggiService.loadPreviousGameBoard();
        }
        return createNewBoard();
    }

    private JanggiBoard createNewBoard() {
        BoardArrangementStrategy strategyOfCho = InputProcessor.repeatUntilNormalInput(() -> inputView.getBoardArrangementInput(JanggiSide.CHO), OutputView::printErrorMessage);
        BoardArrangementStrategy strategyOfHan = InputProcessor.repeatUntilNormalInput(() -> inputView.getBoardArrangementInput(JanggiSide.HAN), OutputView::printErrorMessage);
        outputView.printInitBoardMessage();
        return janggiService.createJanggiBoard(strategyOfCho, strategyOfHan);
    }

    private void processMovePiece(JanggiBoard board, JanggiSide side) {
        InputProcessor.repeatUntilNormalInput(() -> {
            outputView.printTurnMessage(side);
            List<JanggiPosition> originAndDestination = inputView.getMovePieceInput();
            janggiService.movePiece(board, originAndDestination, side);
        } , OutputView::printErrorMessage);
    }

    private void printResult(JanggiSide nowTurn, JanggiBoard board) {
        outputView.printWinningMessage(nowTurn);
        outputView.printScore(JanggiSide.CHO, board.getRemainingPiecesTotalScore(JanggiSide.CHO));
        outputView.printScore(JanggiSide.HAN, board.getRemainingPiecesTotalScore(JanggiSide.HAN));
    }
}
