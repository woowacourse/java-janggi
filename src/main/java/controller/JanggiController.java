package controller;

import domain.JanggiGame;
import domain.Position;
import dto.SelectPositionRequest;
import exception.JanggiGameException;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final JanggiGame janggiGame;

    public JanggiController(JanggiGame janggiGame) {
        this.janggiGame = janggiGame;
    }

    public void run() {
        while (!janggiGame.isGameFinished()) {
            runPlayingPhase();
        }
        runResultPhase();
    }

    private void runPlayingPhase() {
        displayCurrentGameState();
        execute(this::movePiece);
    }

    private void displayCurrentGameState() {
        OutputView.printBoard(janggiGame.allFactors());
        OutputView.printCurrentPlayerTurn(janggiGame.currentPlayerTurn());
    }

    private void movePiece() {
        SelectPositionRequest selectRequest = InputView.selectPiecePosition();
        Position selected = Position.of(selectRequest.row(), selectRequest.col());

        SelectPositionRequest targetRequest = InputView.selectTargetPositionOf(janggiGame.findPieceInfoAt(selected));
        Position target = Position.of(targetRequest.row(), targetRequest.col());

        janggiGame.move(selected, target);
    }

    private void runResultPhase() {
        OutputView.printBoard(janggiGame.allFactors());
        OutputView.printResult(janggiGame.gameStatus());
    }

    private void execute(ExecutableTask task) {
        while (true) {
            try {
                task.execute();
                OutputView.printTaskDivider();
                return;
            } catch (JanggiGameException e) {
                OutputView.printError(e.getMessage());
                OutputView.printTaskDivider();
            }
        }
    }
}
