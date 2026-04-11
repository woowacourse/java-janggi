package janggi.controller;

import janggi.service.JanggiService;
import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.GameResult;
import janggi.view.dto.GameRoom;
import janggi.view.dto.PositionRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class JanggiController {
    private final JanggiService janggiService;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(JanggiService janggiService, InputView inputView, OutputView outputView) {
        this.janggiService = janggiService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        try {
            Long gameId = lobby();
            playJanggiGame(gameId);
        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
        }
    }

    private Long lobby() {
        List<GameRoom> gameRooms = janggiService.findAllGames();
        Long gameId = inputView.readRoomNumber(gameRooms);
        if (gameId == 0L) {
            return initializeJanggi();
        }
        outputView.printEnterGameRoom(gameId);
        return gameId;
    }

    private Long initializeJanggi() {
        int choFormation = inputView.readFormationChoice(1);
        int hanFormation = inputView.readFormationChoice(2);
        return janggiService.start(choFormation, hanFormation);
    }

    private void playJanggiGame(Long gameId) {
        Optional<GameResult> gameResult = Optional.empty();
        while (janggiService.isOngoing(gameId)) {
             gameResult = playTurn(gameId);
        }
        if (gameResult.isPresent()) {
            outputView.printGameResult(gameResult.orElseThrow());
            return;
        }
        outputView.printEndGame();
    }

    private Optional<GameResult> playTurn(Long gameId) {
        Camp currentCamp = janggiService.currentTurn(gameId);
        List<Integer> displayRows = IntStream.rangeClosed(0, 9)
                .map(i -> currentCamp.calculateRow(9 - i))
                .boxed()
                .toList();
        outputView.printBoard(janggiService.getBoardStatus(gameId), displayRows);
        return selectAndMove(gameId);
    }

    private Optional<GameResult> selectAndMove(Long gameId) {
        PositionRequest fromRequest = inputView.readPieceSelection();
        if (fromRequest.howPlaying().equals("q")) {
            return Optional.of(janggiService.giveUpGame(gameId));
        }
        if (fromRequest.howPlaying().equals("d")) {
            return drawHandling(gameId);
        }
        return processMove(gameId, fromRequest);
    }

    private Optional<GameResult> drawHandling(Long gameId) {
        if (inputView.readAcceptDrawRequest()) {
            return Optional.of(janggiService.drawGame(gameId));
        }
        return Optional.empty();
    }

    private Optional<GameResult> processMove(Long gameId, PositionRequest fromRequest) {
        try {
            Optional<PositionRequest> toRequest = inputView.readMoveDestination();
            if (toRequest.isEmpty()) {
                return Optional.empty();
            }
            janggiService.move(gameId,
                    Position.of(fromRequest.row(), fromRequest.column()),
                    Position.of(toRequest.get().row(), toRequest.get().column()));
            return janggiService.checkMatchResult(gameId);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
        return Optional.empty();
    }
}
