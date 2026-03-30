package view;

import board.SangSetup;

public class JanggiView {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiView(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public SangSetup askChoSangSetup() {
        return askSangSetup("초");
    }

    public SangSetup askHanSangSetup() {
        return askSangSetup("한");
    }

    private SangSetup askSangSetup(String sideName) {
        return untilSuccess(() -> {
            outputView.askSangSetup(sideName);
            return inputView.readSangSetup();
        });
    }

    public void printBoard(String board) {
        outputView.printBoard(board);
    }

    public <T> T untilSuccess(SupplierWithEx<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    @FunctionalInterface
    public interface SupplierWithEx<T> {
        T get();
    }
}
