package janggi.controller;

import janggi.domain.Camp;
import janggi.domain.Janggi;
import janggi.domain.Position;
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
        Janggi janggi = initializeJanggi();
        playJanggiGame(janggi);
    }

    private Janggi initializeJanggi() {
        int choFormation = inputView.readFormationChoice(1);
        int hanFormation = inputView.readFormationChoice(2);
        return Janggi.start(choFormation, hanFormation);
    }

    private void playJanggiGame(Janggi janggi) {
        Camp currentCamp = Camp.CHO;
        while (janggi.isOnGoing()) {
            currentCamp = playTurn(janggi, currentCamp);
        }
    }

    private Camp playTurn(Janggi janggi, Camp currentCamp) {
        outputView.printBoard(janggi.piecesStatus(), currentCamp);
        return selectAndMove(janggi, currentCamp);
    }

    private Camp selectAndMove(Janggi janggi, Camp currentCamp) {
        Optional<PositionRequest> fromRequest = inputView.readPieceSelection();
        if (fromRequest.isEmpty()) {
            janggi.stopGame();
            return currentCamp;
        }
        return processMove(janggi, fromRequest.get(), currentCamp);
    }

    private Camp processMove(Janggi janggi, PositionRequest fromRequest, Camp currentCamp) {
        if (tryMove(janggi, fromRequest, currentCamp)) {
            return nextCamp(currentCamp);
        }
        return currentCamp;
    }

    private boolean tryMove(Janggi janggi, PositionRequest from, Camp currentCamp) {
        try {
            janggi.validateCamp(Position.of(from.row(), from.column()), currentCamp);
            return executeMoveSequence(janggi, from);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return false;
        }
    }

    private boolean executeMoveSequence(Janggi janggi, PositionRequest fromRequest) {
        Optional<PositionRequest> toRequest = inputView.readMoveDestination();
        if (toRequest.isEmpty()) {
            return false;
        }
        return applyMoveToDomain(janggi, fromRequest, toRequest.get());
    }

    private boolean applyMoveToDomain(Janggi janggi, PositionRequest fromRequest, PositionRequest toRequest) {
        try {
            janggi.movePiece(Position.of(fromRequest.row(), fromRequest.column()),
                    Position.of(toRequest.row(), toRequest.column()));
            return true;
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return executeMoveSequence(janggi, fromRequest);
        }
    }

    private Camp nextCamp(Camp currentCamp) {
        if (currentCamp.isCho()) {
            return Camp.HAN;
        }
        return Camp.CHO;
    }
}
