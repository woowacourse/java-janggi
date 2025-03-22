package controller;

import domain.JanggiBoard.JanggiBoard;
import domain.JanggiBoard.JanggiBoardBasicInitializer;
import domain.JanggiPosition;
import domain.piece.JanggiSide;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printInitBoardMessage();
        JanggiBoard board = new JanggiBoard(new JanggiBoardBasicInitializer());
        outputView.printBoard(board.getBoard());
        JanggiSide nowTurn = JanggiSide.CHO;

        while (true) {
            processMovePiece(board, nowTurn);
            outputView.printBoard(board.getBoard());
            if (board.isOpposite궁Captured(nowTurn)) {
                break;
            }
            nowTurn = nowTurn.getOppositeSide();
        }

        outputView.printWinningMessage(nowTurn);
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
