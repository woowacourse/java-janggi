package controller;

import domain.JanggiGame;
import domain.boardgenerator.JanggiBoardGenerator;
import java.util.List;
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
        JanggiGame game = initGame();
        while (true) {
            Command command = retry(() -> Command.find(inputView.readCommand(game.getThisTurnPlayer())));
            if (command == Command.NO) {
                break;
            }
            retry(() -> game.move(inputView.readMovePiecePosition(), inputView.readTargetPosition()));
            outputView.printJanggiBoard(game);
            if (game.isEnd()) {
                outputView.printGameEnd();
                break;
            }
        }
    }

    private JanggiGame initGame() {
        List<String> playerNames = inputView.readPlayerNames();
        JanggiGame game = new JanggiGame(new JanggiBoardGenerator(), playerNames);
        outputView.printGameInfo(playerNames, game);
        return game;
    }

    private <T> T retry(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            retry(supplier);
        }
        return null;
    }

    private void retry(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            retry(runnable);
        }
    }
}
