package controller;

import domain.GameRooms;
import domain.JanggiGame;
import domain.dao.GamesDaoImpl;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        GameRooms gameRooms = new GameRooms(new GamesDaoImpl());
        JanggiGame game = retry(() -> startGame(gameRooms));
        outputView.printJanggiBoard(game);
        while (!game.isEnd()) {
            Command command = retry(() -> Command.find(inputView.readCommand(game.getThisTurnTeam())));
            if (command == Command.QUIT) {
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

    private JanggiGame startGame(GameRooms gameRooms) {
        if (gameRooms.isEmpty()) {
            return gameRooms.createRoom(inputView.readNewRoomName());
        }
        NewOrContinue newOrContinue = NewOrContinue.find(inputView.readNewOrContinueGame());
        if (newOrContinue == NewOrContinue.NEW) {
            return gameRooms.createRoom(inputView.readNewRoomName());
        }
        outputView.printAllRoomNames(gameRooms.findAllRoomNames());
        return gameRooms.findByName(inputView.readRoomName());
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
