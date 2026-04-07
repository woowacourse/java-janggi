package janggi.controller;

import janggi.view.OutputView;
import java.util.function.Supplier;

public class ControllerUtil {
    private ControllerUtil() {

    }

    public static void retry(Runnable runnable, OutputView outputView) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            } catch (Exception e) {
                outputView.printError(e.getMessage());
                throw new IllegalStateException("시스템상의 오류로 게임을 종료합니다.");
            }
        }
    }

    public static <T> T retry(Supplier<T> supplier, OutputView outputView) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            } catch (Exception e) {
                outputView.printError(e.getMessage());
                throw new IllegalStateException("시스템상의 오류로 게임을 종료합니다.");
            }
        }
    }
}
