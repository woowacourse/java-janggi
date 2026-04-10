package janggi.controller;

import janggi.domain.Camp;
import janggi.domain.Janggi;
import janggi.domain.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.PositionRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        try {
            Janggi janggi = initializeJanggi();
            playJanggiGame(janggi);
        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
        }
    }

    private Janggi initializeJanggi() {
        int choFormation = inputView.readFormationChoice(1);
        int hanFormation = inputView.readFormationChoice(2);
        return Janggi.start(choFormation, hanFormation);
    }

    private void playJanggiGame(Janggi janggi) {
        while (janggi.isOnGoing()) {
             playTurn(janggi);
        }
    }

    private void playTurn(Janggi janggi) {
        Camp currentCamp = janggi.currentTurn();
        List<Integer> displayRows = IntStream.rangeClosed(0, 9)
                .map(i -> currentCamp.calculateRow(9 - i))
                .boxed()
                .toList();
        outputView.printBoard(janggi.piecesStatus(), displayRows);
        selectAndMove(janggi);
    }

    private void selectAndMove(Janggi janggi) {
        PositionRequest fromRequest = inputView.readPieceSelection();
        if (fromRequest.howPlaying().equals("q")) {
            outputView.printGameResult(janggi.processGiveUpRequest());
            return;
        }
        if (fromRequest.howPlaying().equals("d")) {
            drawHandling(janggi);
            return;
        }
        processMove(janggi, fromRequest);
    }

    private void drawHandling(Janggi janggi) {
        if (inputView.readAcceptDrawRequest()) {
            janggi.drawGame();
            outputView.printGameResult(janggi.calculateGameResult());
        }
    }

    private void processMove(Janggi janggi, PositionRequest fromRequest) {
        tryMove(janggi, fromRequest);
    }

    private void tryMove(Janggi janggi, PositionRequest from) {
        try {
            janggi.validateCamp(Position.of(from.row(), from.column()));
            executeMoveSequence(janggi, from);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private void executeMoveSequence(Janggi janggi, PositionRequest fromRequest) {
        Optional<PositionRequest> toRequest = inputView.readMoveDestination();
        if (toRequest.isEmpty()) {
            return;
        }
        applyMoveToDomain(janggi, fromRequest, toRequest.get());
    }

    private void applyMoveToDomain(Janggi janggi, PositionRequest fromRequest, PositionRequest toRequest) {
        try {
            janggi.movePiece(Position.of(fromRequest.row(), fromRequest.column()),
                    Position.of(toRequest.row(), toRequest.column()));
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            executeMoveSequence(janggi, fromRequest);
        }
    }
}
