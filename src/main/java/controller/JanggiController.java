package controller;

import domain.JanggiBoard;
import domain.JanggiGame;
import domain.JanggiService;
import domain.dao.GameDaoImpl;
import domain.dao.PieceDaoImpl;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = new JanggiService(new GameDaoImpl(), new PieceDaoImpl());
    }

    public void run() {
        JanggiGame game = retry(this::startGame);
        outputView.printJanggiBoard(game);
        while (!game.isEnd()) {
            Command command = retry(() -> Command.find(inputView.readCommand(game.getThisTurnTeam())));
            if (command == Command.QUIT) {
                JanggiBoard board = game.getBoard();
                janggiService.saveGame(game, board.getPieces());
                break;
            }
            if (command == Command.SCORE) {
                outputView.printScore(game.calculateScore());
                continue;
            }
            retry(() -> game.move(inputView.readMovePiecePosition(), inputView.readTargetPosition()));
            outputView.printJanggiBoard(game);
        }
        if (game.isEnd()) {
            outputView.printGameEnd();
        }
    }

    private JanggiGame startGame() {
        if (janggiService.isEmpty()) {
            return janggiService.addGame(inputView.readNewRoomName());
        }
        NewOrContinue newOrContinue = NewOrContinue.find(inputView.readNewOrContinueGame());
        if (newOrContinue == NewOrContinue.NEW) {
            return janggiService.addGame(inputView.readNewRoomName());
        }
        outputView.printAllRoomNames(janggiService.findAllRoomNames());
        return janggiService.findGameByName(inputView.readRoomName());
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    private void retry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
    }
}
