package controller;

import domain.JanggiGame;
import domain.Team;
import domain.board.Point;
import java.util.List;
import java.util.Map;
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
    outputView.printTurnGuide();
    final JanggiGame game = setupGame();
    outputView.printBoard(game.getBoard());
    while (true) {
      processMove(game);
    }
  }

  private JanggiGame setupGame() {
    final Map<Team, Integer> choicesForSetup = inputView.readChoicesForSetup();
    return JanggiGame.setup(choicesForSetup);
  }

  private void processMove(final JanggiGame game) {
    final List<List<Integer>> movementRequest = inputView.readMovementRequest();
    final Point originPoint = getOriginPoint(movementRequest);
    final Point arrivalPoint = getArrivalPoint(movementRequest);

    game.move(originPoint, arrivalPoint);

    outputView.printBoard(game.getBoard());
  }

  private Point getOriginPoint(final List<List<Integer>> movementRequest) {
    final List<Integer> originPointRequest = movementRequest.getFirst();
    return new Point(originPointRequest.getFirst(),
        originPointRequest.getLast());
  }

  private Point getArrivalPoint(final List<List<Integer>> movementRequest) {
    final List<Integer> arrivalPointRequest = movementRequest.getLast();
    return new Point(arrivalPointRequest.getFirst(),
        arrivalPointRequest.getLast());
  }
}
