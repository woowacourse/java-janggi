package config;

import controller.JanggiController;
import exception.GameExceptionHandler;
import view.InputView;
import view.OutputView;

public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public JanggiController janggiController() {
        return new JanggiController(gameExceptionHandler(), inputView(), outputView());
    }

    public GameExceptionHandler gameExceptionHandler() {
        return new GameExceptionHandler(outputView());
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }
}
