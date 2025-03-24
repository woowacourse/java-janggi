package controller;

import domain.janggiboard.JanggiBoard;
import domain.janggiboard.JanggiBoardBasicInitializer;
import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import domain.position.JanggiPosition;
import domain.piece.JanggiSide;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {

    public static final JanggiSide JANGGI_GAME_STARTING_SIDE = JanggiSide.CHO;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        JanggiBoard board = initializeJanggiBoard();
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

        outputView.printWinningMessage(nowTurn);
    }

    private JanggiBoard initializeJanggiBoard() {
        BoardArrangementStrategy strategyOfCho = InputProcessor.repeatUntilNormalInput(() -> inputView.getBoardArrangementInput(JanggiSide.CHO), OutputView::printErrorMessage);
        BoardArrangementStrategy strategyOfHan = InputProcessor.repeatUntilNormalInput(() -> inputView.getBoardArrangementInput(JanggiSide.HAN), OutputView::printErrorMessage);
        outputView.printInitBoardMessage();

        return new JanggiBoard(new JanggiBoardBasicInitializer(strategyOfCho, strategyOfHan));
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
        } , OutputView::printErrorMessage);
    }
}
