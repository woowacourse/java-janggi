package controller;

import java.util.function.Supplier;

import view.InputView;
import view.OutputView;

public class JanggiController {
    private static final int MAX_RETRY = 10;

    private final InputView inputView;
    private final OutputView outputView;


    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        init();


    }

    private void init() {
        outputView.printStartMessage();

        int choMasangChoice = doRetry(inputView::requestMaSangPosition);
        int hanMasangChoice = doRetry(inputView::requestMaSangPosition);

        // TODO: 장기판 초기화

    }


    private <T> T doRetry(Supplier<T> supplier) {
        int retry = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e){
                outputView.printErrorMessage(e.getMessage());
                retry++;
                if (retry > MAX_RETRY) {
                    throw new IllegalStateException("입력횟수를 초과했습니다.");
                }
            }
        }
    }


}
