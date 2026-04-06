package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.strategy.BasicPlacementStrategy;
import janggi.service.JanggiService;
import janggi.util.RetryExecutor;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Map;

public class JanggiController {
    private static final long NEW_GAME_ID = 0;

    private final JanggiService janggiService;
    private boolean isExit = false;

    public JanggiController(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    public void run() {
        long gameId = initializeGame();
        while (isGameContinue()) {
            gameId = executeTurn(gameId);
        }
    }

    private long initializeGame() {
        List<Long> savedGameIds = janggiService.getSavedGameIds();

        if (savedGameIds.isEmpty()) {
            return startAutoNewGame();
        }

        return selectGameOrNew(savedGameIds);
    }

    private long startAutoNewGame() {
        OutputView.printEmptySavedGame();
        Board basicInitBoard = new Board(new BasicPlacementStrategy());
        janggiService.startNewGame(basicInitBoard);
        return NEW_GAME_ID;
    }

    private long selectGameOrNew(List<Long> savedGameIds) {
        OutputView.printSavedGames(savedGameIds);
        long selectedId = RetryExecutor.retry(InputView::askLoadGameId);
        return setupGameBySelection(selectedId);
    }

    private long setupGameBySelection(long selectedId) {
        if (selectedId == NEW_GAME_ID) {
            Board basicInitBoard = new Board(new BasicPlacementStrategy());
            janggiService.startNewGame(basicInitBoard);
            return NEW_GAME_ID;
        }

        janggiService.startLoadGame(selectedId);
        return selectedId;
    }

    private boolean isGameContinue() {
        return !isExit && !janggiService.isFinished();
    }

    private long executeTurn(long gameId) {
        playJanggi();
        return processSave(gameId);
    }

    private void playJanggi() {
        RetryExecutor.retry(() -> {
            printCurrentBoard();

            Position from = RetryExecutor.retry(InputView::askSelectPiece);
            Position to = RetryExecutor.retry(InputView::askTargetPosition);
            janggiService.movePiece(from, to);

            printCurrentBoard();
        });
    }

    private void printCurrentBoard() {
        Map<Position, Space> capturedBoard = janggiService.getBoardDto();
        String currentTurn = janggiService.getCurrentTurn();
        double currentTurnScore = janggiService.getCurrentTurnScore();
        OutputView.printBoard(capturedBoard, currentTurn, currentTurnScore);
    }

    private long processSave(long gameId) {
        boolean isSave = RetryExecutor.retry(InputView::askSaveGame);
        if (isSave) {
            gameId = saveGame(gameId);
            isExit = RetryExecutor.retry(InputView::askExitAfterSave);
        }
        return gameId;
    }

    private long saveGame(long currentGameId) {
        try {
            long targetId = determineTargetId(currentGameId);
            janggiService.saveGame(targetId);
            OutputView.printSaveSuccess(targetId);
            return targetId;
        } catch (Exception e) {
            OutputView.printErrorMessage("저장 실패: " + e.getMessage());
            return currentGameId;
        }
    }

    private long determineTargetId(long currentGameId) {
        if (currentGameId != NEW_GAME_ID && RetryExecutor.retry(InputView::askOverwrite)) {
            return currentGameId;
        }
        return getUniqueNewGameId();
    }

    private long getUniqueNewGameId() {
        return RetryExecutor.retry(() -> {
            long newId = InputView.askGenerateGameId();
            if (newId <= 0) {
                throw new IllegalArgumentException("ID는 0보다 큰 숫자여야 합니다.");
            }
            if (janggiService.isDuplicateId(newId)) {
                throw new IllegalArgumentException("이미 존재하는 게임 ID입니다. 다른 번호를 입력해주세요.");
            }
            return newId;
        });
    }
}
