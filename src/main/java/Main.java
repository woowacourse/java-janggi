import config.AppConfig;
import domain.console.GameConsole;

public class Main {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        GameConsole gameConsole = appConfig.gameConsole();
        gameConsole.run();
    }
}
