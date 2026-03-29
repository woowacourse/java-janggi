package config;

import controller.JanggiController;
import view.InputView;
import view.OutputView;

public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private AppConfig(){
    }

    public static AppConfig getInstance(){
        return INSTANCE;
    }

    public JanggiController janggiController(){
        return new JanggiController(inputView(),outputView());
    }

    public InputView inputView(){
        return new InputView();
    }

    public OutputView outputView(){
        return new OutputView();
    }
}
