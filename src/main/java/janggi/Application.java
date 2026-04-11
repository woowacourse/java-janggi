package janggi;

import janggi.config.AppConfig;

public class Application {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        GameManager gameManager = appConfig.gameManager();
        gameManager.run();
    }
}
