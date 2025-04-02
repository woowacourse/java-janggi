package controller;

import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import service.JanggiService;
import view.GameContinueOption;
import view.InputView;
import view.OutputView;

import java.util.List;

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
        initializeJanggiBoard();

        JanggiSide nowTurn = JANGGI_GAME_STARTING_SIDE;
        processJanggiGame(nowTurn);

        printResult(nowTurn);
        janggiService.finishGame();
    }

    private void initializeJanggiBoard() {
        if (janggiService.isPreviousGameNotOver()) {
            initializeJanggiBoardWhenPreviousGameExist();
            return;
        }
        createNewBoard();
        outputView.printBoard(janggiService.getBoard());
    }

    private void initializeJanggiBoardWhenPreviousGameExist() {
        GameContinueOption continueSelection = InputProcessor.repeatUntilNormalInput(
                inputView::getPreviousGameContinueSelectionInput,
                OutputView::printErrorMessage
        );
        if (continueSelection == GameContinueOption.Y) {
            janggiService.loadPreviousGameBoard();
            outputView.printBoard(janggiService.getBoard());
            return;
        }
        createNewBoard();
    }

    private void createNewBoard() {
        BoardArrangementStrategy strategyOfCho = InputProcessor.repeatUntilNormalInput(() -> inputView.getBoardArrangementInput(JanggiSide.CHO), OutputView::printErrorMessage);
        BoardArrangementStrategy strategyOfHan = InputProcessor.repeatUntilNormalInput(() -> inputView.getBoardArrangementInput(JanggiSide.HAN), OutputView::printErrorMessage);
        janggiService.startGame(strategyOfCho, strategyOfHan);
        outputView.printInitBoardMessage();
    }

    private void processJanggiGame(JanggiSide nowTurn) {
        while (true) {
            processMovePiece(nowTurn);
            outputView.printBoard(janggiService.getBoard());
            if (janggiService.isOppositeKingCaptured(nowTurn)) {
                break;
            }
            nowTurn = nowTurn.getOppositeSide();
        }
    }

    private void processMovePiece(JanggiSide side) {
        InputProcessor.repeatUntilNormalInput(() -> {
            outputView.printTurnMessage(side);
            List<JanggiPosition> originAndDestination = inputView.getMovePieceInput();
            janggiService.movePiece(originAndDestination, side);
        }, OutputView::printErrorMessage);
    }

    private void printResult(JanggiSide nowTurn) {
        outputView.printWinningMessage(nowTurn);
        outputView.printScore(JanggiSide.CHO, janggiService.getRemainingPiecesTotalScore(JanggiSide.CHO));
        outputView.printScore(JanggiSide.HAN, janggiService.getRemainingPiecesTotalScore(JanggiSide.HAN));
    }
}
