package janggi.controller;

import janggi.dto.MoveCommandDto;
import janggi.service.JanggiService;
import janggi.view.GameRunningView;

public class GamePlayController {
    private final GameRunningView gameRunningView;
    private final JanggiService janggiService;

    public GamePlayController(GameRunningView gameRunningView, JanggiService janggiService) {
        this.gameRunningView = gameRunningView;
        this.janggiService = janggiService;
    }

    public void run() {
        gameRunningView.printBoard(janggiService.getBoard());

        while (!janggiService.isGameFinished()) {
            processWithRetry(this::playSingleTurn);
        }

        gameRunningView.printGameResult(janggiService.getGameResult());
        janggiService.finishGame();
    }

    private void playSingleTurn() {
        gameRunningView.printTurnNotice(janggiService.getCurrentTurn());
        String input = gameRunningView.readCommand();
        GameCommand command = GameCommand.from(input);

        if(command == GameCommand.MOVE) {
            MoveCommandDto moveDto = MoveCommandDto.from(input);
            playTurn(moveDto);
        }
        if(command == GameCommand.QUIT) {
            gameQuit();
        }
    }

    private void playTurn(MoveCommandDto commandDto) {
        janggiService.movePiece(commandDto);
        gameRunningView.printBoard(janggiService.getBoard());
    }

    private void gameQuit() {
        System.out.println("게임 종료");
        System.exit(0);
    }

    private static void processWithRetry(Runnable runnable) {
        int attempts = 0;
        while (attempts < 100) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("[Error] " + e.getMessage() + "\n");
                attempts++;
            }
        }
        throw new IllegalStateException("최대 재시도 횟수(100)를 초과했습니다. 프로그램을 종료합니다.");
    }
}
