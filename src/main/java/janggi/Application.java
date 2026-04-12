package janggi;

import janggi.config.AppConfig;

public class Application {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        GameRunner gameRunner = appConfig.gameManager();
        gameRunner.run();
    }
}
