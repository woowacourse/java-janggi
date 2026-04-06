package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.strategy.BasicPlacementStrategy;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map;

public class JanggiController {
    private final JanggiService janggiService;

    public JanggiController(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    public void run() {
        long gameId = 1;
        janggiService.startNewGame(new Board(new BasicPlacementStrategy()));

        while (!janggiService.isFinished()) {
            playJanggi();

            String command = RetryExecutor.retry(InputView::askSaveGame);
            if ("y".equalsIgnoreCase(command)) {
                saveGame(gameId);
                break;
            }
        }
    }

    private void playJanggi() {
        try {
            Map<Position, Space> capturedBoard = janggiService.getBoardDto();
            OutputView.printBoard(capturedBoard);

            Position from = RetryExecutor.retry(InputView::askSelectPiece);
            Position to = RetryExecutor.retry(InputView::askTargetPosition);
            janggiService.movePiece(from, to);

            capturedBoard = janggiService.getBoardDto();
            OutputView.printBoard(capturedBoard);
        } catch (Exception e) {
            OutputView.printErrorMessage(e.getMessage());
        }
    }

    private void saveGame(long gameId) {
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
