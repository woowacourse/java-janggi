import global.config.DiConfig;
import domain.janggigame.JanggiGame;

public class Main {
    public static void main(String[] args) {
        DiConfig diConfig = new DiConfig();
        JanggiGame janggiGame = diConfig.janggiGame();
        janggiGame.run();
    }
}
