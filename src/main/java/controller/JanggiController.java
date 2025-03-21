package controller;

import domain.JanggiBoard.JanggiBoard;
import domain.JanggiBoard.JanggiBoardBasicInitializer;
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
        // 보드판 초기화
        outputView.printInitBoardMessage();
        JanggiBoard board = new JanggiBoard(new JanggiBoardBasicInitializer());
        // 보드판 보여주기
        outputView.printBoard(board.getBoard());
        // (반복) 초, 한 각각 이동, 이동후 보드판 보여주기

        // 왕 잡히면 종료
    }
}
