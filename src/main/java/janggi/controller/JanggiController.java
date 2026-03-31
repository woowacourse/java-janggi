package janggi.controller;

import janggi.domain.Camp;
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
        int choFormation = inputView.readFormationChoice(1);
        int hanFormation = inputView.readFormationChoice(2);
        Janggi janggi = Janggi.start(choFormation, hanFormation);

        Camp currentCamp = Camp.CHO;
        while (true) {
            outputView.printBoard(janggi.getBoard(), currentCamp);

            PositionRequest from = selectPiece(janggi, currentCamp);
            boolean moved = tryMove(janggi, from);
            if (moved) {
                currentCamp = currentCamp.next();
            }
        }
    }

    private PositionRequest selectPiece(Janggi janggi, Camp currentCamp) {
        while (true) {
            Optional<PositionRequest> request = inputView.readPieceSelection();
            if (request.isEmpty()) {
                continue;
            }
            PositionRequest positionRequest = request.get();
            try {
                janggi.validateCamp(positionRequest.row(), positionRequest.column(), currentCamp);
                return positionRequest;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean tryMove(Janggi janggi, PositionRequest from) {
        while (true) {
            Optional<PositionRequest> request = inputView.readMoveDestination();
            if (request.isEmpty()) {
                return false;
            }
            PositionRequest to = request.get();
            try {
                janggi.movePiece(to.row(), to.column(), Position.of(from.row(), from.column()));
                return true;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
