package janggi;

import janggi.domain.Player;
import janggi.domain.Players;
import janggi.domain.Side;
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
        String choPlayerName = readPlayerName(Side.CHO);
        String hanPlayerName = readPlayerName(Side.HAN);
        return new Players(choPlayerName, hanPlayerName);
    }

    private String readPlayerName(Side side) {
        return retry(() -> {
            outputView.printPlayerNameNotice(side.getDisplayName());
            return inputView.readPlayerName();
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
