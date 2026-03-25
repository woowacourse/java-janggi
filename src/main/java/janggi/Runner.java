package janggi;

import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.function.Supplier;

public class Runner {

    private final OutputView outputView;
    private final InputView inputView;

    public Runner(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        Players players = initialPlayers();
    }

    private void initialPlayers() {
        Player choPlayer = new Player(readChoPlayerName());
        Player hanPlayer = new Player(readHanPlayerName());

        return new Players(choPlayer, hanPlayer);
    }

    private String readChoPlayerName() {
        return retry(() -> {
            outputView.printChoPlayerNameNotice();
            return inputView.readChoPlayerName();
        });
    }

    private String readHanPlayerName() {
        return retry(() -> {
            outputView.printHanPlayerNameNotice();
            return inputView.readHanPlayerName();
        });
    }

    private <T> T retry(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printLine(e.getMessage());
            return retry(supplier);
        }
    }
}
