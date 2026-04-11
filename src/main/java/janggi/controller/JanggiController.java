package janggi.controller;

import janggi.domain.Camp;
import janggi.domain.Janggi;
import janggi.domain.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.GameResult;
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
        Optional<GameResult> gameResult = Optional.empty();
        while (janggi.isOnGoing()) {
             gameResult = playTurn(janggi);
        }
        if (gameResult.isPresent()) {
            outputView.printGameResult(gameResult.orElseThrow());
        }
    }

    private Optional<GameResult> playTurn(Janggi janggi) {
        Camp currentCamp = janggi.currentTurn();
        List<Integer> displayRows = IntStream.rangeClosed(0, 9)
                .map(i -> currentCamp.calculateRow(9 - i))
                .boxed()
                .toList();
        outputView.printBoard(janggi.piecesStatus(), displayRows);
        return selectAndMove(janggi);
    }

    private Optional<GameResult> selectAndMove(Janggi janggi) {
        PositionRequest fromRequest = inputView.readPieceSelection();
        if (fromRequest.howPlaying().equals("q")) {
            return Optional.of(janggi.processGiveUpResult());
        }
        if (fromRequest.howPlaying().equals("d")) {
            return drawHandling(janggi);
        }
        return processMove(janggi, fromRequest);
    }

    private Optional<GameResult> drawHandling(Janggi janggi) {
        if (inputView.readAcceptDrawRequest()) {
            janggi.drawGame();
            return Optional.of(janggi.processDrawGameResult());
        }
        return Optional.empty();
    }

    private Optional<GameResult> processMove(Janggi janggi, PositionRequest fromRequest) {
        return tryMove(janggi, fromRequest);
    }

    private Optional<GameResult> tryMove(Janggi janggi, PositionRequest from) {
        try {
            janggi.validateCamp(Position.of(from.row(), from.column()));
            return executeMoveSequence(janggi, from);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GameResult> executeMoveSequence(Janggi janggi, PositionRequest fromRequest) {
        Optional<PositionRequest> toRequest = inputView.readMoveDestination();
        if (toRequest.isEmpty()) {
            return Optional.empty();
        }
        return applyMoveToDomain(janggi, fromRequest, toRequest.get());
    }

    private Optional<GameResult> applyMoveToDomain(Janggi janggi, PositionRequest fromRequest, PositionRequest toRequest) {
        try {
            janggi.movePiece(Position.of(fromRequest.row(), fromRequest.column()),
                    Position.of(toRequest.row(), toRequest.column()));
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return executeMoveSequence(janggi, fromRequest);
        }
        return janggi.processCheckmateResult();
    }
}
