package janggi;

import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final SetupOption setupOption = SetupOption.of(inputView.readSetupOption());
        final Board board = BoardGenerator.generate(setupOption);
        outputView.displayBoard(board.getBoard());
    }
}
