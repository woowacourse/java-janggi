package controller;

import domain.JanggiGame;
import domain.board.BoardPoint;
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
        final JanggiGame game = new JanggiGame();
        outputView.printBoard(game.getBoard());
        while (true) {
            processMove(game);
        }
    }

    private void processMove(final JanggiGame game) {
        final List<BoardPoint> movementRequest = inputView.readMovementRequest();
        final BoardPoint startBoardPoint = movementRequest.getFirst();
        final BoardPoint arrivalBoardPoint = movementRequest.getLast();

        game.move(startBoardPoint, arrivalBoardPoint);

        outputView.printBoard(game.getBoard());
    }
}
