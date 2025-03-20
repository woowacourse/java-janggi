package controller;

import domain.JanggiGame;
import domain.board.Point;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        JanggiGame game = new JanggiGame();
        outputView.printBoard(game.getBoard());
        boolean isFirstPlayerTurn = true;
        while (true) {
            processMove(game, isFirstPlayerTurn);
            isFirstPlayerTurn = !isFirstPlayerTurn;
        }

    }

    private void processMove(JanggiGame game, boolean isFirstPlayerTurn) {
        List<List<Integer>> movementRequest = inputView.readMovementRequest();
        List<Integer> originPointRequest = movementRequest.getFirst();
        Point originPoint = new Point(originPointRequest.getFirst(), originPointRequest.getLast());

        List<Integer> arrivalPointRequest = movementRequest.getLast();
        Point arrivalPoint = new Point(arrivalPointRequest.getFirst(), arrivalPointRequest.getLast());

        game.move(originPoint, arrivalPoint, isFirstPlayerTurn);

        outputView.printBoard(game.getBoard());
    }
}
