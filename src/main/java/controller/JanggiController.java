package controller;

import domain.janggiboard.JanggiBoard;
import domain.janggiboard.JanggiBoardBasicInitializer;
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

    public JanggiController(final InputView inputView, final OutputView outputView, final JanggiService janggiService) {
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
            if (board.isOppositeKingCaptured(nowTurn)) {
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
        GameContinueOption continueSelection = inputView.getPreviousGameContinueSelectionInput();
        if (continueSelection == GameContinueOption.Y) {
            return loadPreviousGameBoard();
        }
       return createNewBoard();
    }

    private JanggiBoard createNewBoard() {
        JanggiBoard board;
        BoardArrangementStrategy strategyOfCho = InputProcessor.repeatUntilNormalInput(() -> inputView.getBoardArrangementInput(JanggiSide.CHO), OutputView::printErrorMessage);
        BoardArrangementStrategy strategyOfHan = InputProcessor.repeatUntilNormalInput(() -> inputView.getBoardArrangementInput(JanggiSide.HAN), OutputView::printErrorMessage);
        board = new JanggiBoard(new JanggiBoardBasicInitializer(strategyOfCho, strategyOfHan));
        janggiService.startGame(strategyOfCho, strategyOfHan);
        outputView.printInitBoardMessage();
        return board;
    }

    private JanggiBoard loadPreviousGameBoard() {
        BoardArrangementStrategy strategyOfCho = janggiService.getChoStrategy();
        BoardArrangementStrategy strategyOfHan = janggiService.getHanStrategy();
        JanggiBoard board = new JanggiBoard(new JanggiBoardBasicInitializer(strategyOfCho, strategyOfHan));
        outputView.printInitBoardMessage();

        List<List<JanggiPosition>> histories = janggiService.getHistories();
        for (List<JanggiPosition> history : histories) {
            JanggiPosition origin = history.get(0);
            JanggiPosition destination = history.get(1);
            board.movePiece(origin, destination);
        }
        return board;
    }

    private void processMovePiece(JanggiBoard board, JanggiSide side) {
        InputProcessor.repeatUntilNormalInput(() -> {
            outputView.printTurnMessage(side);
            List<JanggiPosition> originAndDestination = inputView.getMovePieceInput();
            JanggiPosition origin = originAndDestination.get(0);
            JanggiPosition destination = originAndDestination.get(1);
            if (!board.isSameTeam(origin, side)) {
                throw new IllegalArgumentException("차례에 맞는 말을 선택하세요.");
            }
            board.movePiece(origin, destination);
            janggiService.addHistory(origin, destination);
        } , OutputView::printErrorMessage);
    }

    private void printResult(JanggiSide nowTurn, JanggiBoard board) {
        outputView.printWinningMessage(nowTurn);
        outputView.printScore(JanggiSide.CHO, board.getRemainingPiecesTotalScore(JanggiSide.CHO));
        outputView.printScore(JanggiSide.HAN, board.getRemainingPiecesTotalScore(JanggiSide.HAN));
    }
}
