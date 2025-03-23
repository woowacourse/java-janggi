package controller;

import domain.JanggiGame;
import domain.board.Point;
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
        boolean isFirstPlayerTurn = true;
        while (true) {
            processMove(game, isFirstPlayerTurn);
            isFirstPlayerTurn = !isFirstPlayerTurn;
        }
    }

    private void processMove(final JanggiGame game, final boolean isFirstPlayerTurn) {
        final List<Point> movementRequest = inputView.readMovementRequest();
        final Point startPoint = movementRequest.getFirst();
        final Point arrivalPoint = movementRequest.getLast();

        game.move(startPoint, arrivalPoint, isFirstPlayerTurn);

        outputView.printBoard(game.getBoard());
    }
}
