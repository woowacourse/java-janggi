import controller.JanggiController;
import global.config.DiConfig;

public class Main {
    public static void main(String[] args) {
        DiConfig diConfig = new DiConfig();
        JanggiController janggiController = diConfig.janggiController();
        janggiController.run();
    }
}
