package config;

import controller.JanggiController;
import view.InputView;

public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private AppConfig(){
    }

    public static AppConfig getInstance(){
        return INSTANCE;
    }

    public JanggiController janggiController(){
        return new JanggiController(inputView());
    }

    public InputView inputView(){
        return new InputView();
    }
}
