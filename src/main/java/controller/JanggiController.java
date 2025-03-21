package controller;

import domain.JanggiBoard.JanggiBoard;
import domain.JanggiBoard.JanggiBoardBasicInitializer;
import domain.JanggiPosition;
import domain.piece.JanggiSide;
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

        while (true) {
            processOneTurn(board);
        }
    }

    private void processOneTurn(JanggiBoard board) {
        for (JanggiSide side : JanggiSide.getValidSides()) {
            movePiece(board, side);
            outputView.printBoard(board.getBoard());
        }
    }

    private void movePiece(JanggiBoard board, JanggiSide side) {
        InputProcessor.repeatUntilNormalInput(() -> {
            outputView.printTurnMessage(side);
            JanggiPosition origin = inputView.getMovePieceInput();
            if (!board.isSameTeam(origin, side)) {
                throw new IllegalArgumentException("차례에 맞는 말을 선택하세요.");
            }

            JanggiPosition destination = inputView.getDestinationInput();
            board.movePiece(origin, destination);
        } , OutputView::printErrorMessage);
    }
}
