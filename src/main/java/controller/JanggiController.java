package controller;

import domain.JanggiGame;
import domain.Player;
import domain.Position;
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
        List<String> playerNames = inputView.readPlayerNames();
        JanggiGame janggiGame = new JanggiGame(new JanggiBoardGenerator(), playerNames);
        outputView.displayPlayerInfo(playerNames);
        outputView.printJanggiBoard(janggiGame.getBoardState());
        while (true) {
            Command command = retry(() -> Command.find(inputView.readCommand(janggiGame.getThisTurnPlayer())));
            if (command == Command.NO) {
                break;
            }
            retry(() -> janggiGame.move(inputView.readMovePiecePosition(), inputView.readTargetPosition()));
            outputView.printJanggiBoard(janggiGame.getBoardState());
        }
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
