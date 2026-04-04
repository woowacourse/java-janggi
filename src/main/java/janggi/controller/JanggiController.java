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

            String command = InputView.askSaveGame();
            if ("y".equals(command)) {
                saveGame(gameId);
                break;
            }
        }
    }

    private void playJanggi() {
        try {
            Map<Position, Space> capturedBoard = janggiService.getBoardDto();
            OutputView.printBoard(capturedBoard);

            Position from = InputView.askSelectPiece();
            Position to = InputView.askTargetPosition();
            janggiService.movePiece(from, to);

            capturedBoard = janggiService.getBoardDto();
            OutputView.printBoard(capturedBoard);
        } catch (Exception e) {
            OutputView.printErrorMessage(e.getMessage());
        }
    }

    private void saveGame(long gameId) {
        try {
            janggiService.saveGame(gameId);
            System.out.println("게임이 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            OutputView.printErrorMessage("저장 실패: " + e.getMessage());
        }
    }
}
