package janggi.controller;

import janggi.domain.Janggi;
import janggi.domain.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.PositionRequest;

import java.util.Optional;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Janggi janggi = createGame();

        while (janggi.isRunning()) {
            outputView.printBoard(janggi.getBoard(), janggi.currentCamp());
            playTurn(janggi);
        }
    }

    private Janggi createGame() {
        int choFormation = inputView.readFormationChoice(1);
        int hanFormation = inputView.readFormationChoice(2);
        return Janggi.start(choFormation, hanFormation);
    }

    private void playTurn(Janggi janggi) {
        while (true) {
            try {
                Optional<PositionRequest> selection = inputView.readPieceSelection();
                if (selection.isEmpty()) {
                    continue;
                }

                Optional<PositionRequest> destination = inputView.readMoveDestination();
                if (destination.isEmpty()) {
                    continue;
                }

                Position from = Position.of(selection.get().row(), selection.get().column());
                Position to = Position.of(destination.get().row(), destination.get().column());

                janggi.play(from, to);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
