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
    final List<List<Integer>> movementRequest = inputView.readMovementRequest();
    final List<Integer> originPointRequest = movementRequest.getFirst();
    final Point originPoint = new Point(originPointRequest.getFirst(),
        originPointRequest.getLast());

    final List<Integer> arrivalPointRequest = movementRequest.getLast();
    final Point arrivalPoint = new Point(arrivalPointRequest.getFirst(),
        arrivalPointRequest.getLast());

    game.move(originPoint, arrivalPoint, isFirstPlayerTurn);

    outputView.printBoard(game.getBoard());
  }
}
