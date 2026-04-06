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

    public JanggiController(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    public void run() {
        long gameId = initializeGame();

        while (!janggiService.isFinished()) {
            playJanggi();

            boolean isSave = RetryExecutor.retry(InputView::askSaveGame);
            if (isSave) {
                gameId = saveGame(gameId);
                if (InputView.askExitAfterSave()) {
                    break;
                }
            }
        }
    }

    private long initializeGame() {
        List<Long> savedGameIds = janggiService.getSavedGameIds();

        if (savedGameIds.isEmpty()) {
            System.out.println("저장된 게임이 없습니다. 새로운 게임을 시작합니다.");
            janggiService.startNewGame(new Board(new BasicPlacementStrategy()));
            return NEW_GAME_ID;
        }

        OutputView.printSavedGames(savedGameIds);
        long selectedId = RetryExecutor.retry(InputView::askLoadGameId);

        if (selectedId == NEW_GAME_ID) {
            janggiService.startNewGame(new Board(new BasicPlacementStrategy()));
            return NEW_GAME_ID;
        }

        janggiService.startLoadGame(selectedId);
        return selectedId;
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

    private long saveGame(long currentGameId) {
        try {
            long targetId = currentGameId;
            if (currentGameId != 0) {
                if (!RetryExecutor.retry(InputView::askOverwrite)) {
                    targetId = getUniqueNewGameId();
                }
            } else {
                targetId = getUniqueNewGameId();
            }

            janggiService.saveGame(targetId);
            System.out.println(targetId + " 게임이 성공적으로 저장되었습니다.");
            return targetId;
        } catch (Exception e) {
            OutputView.printErrorMessage("저장 실패: " + e.getMessage());
            return currentGameId;
        }
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
