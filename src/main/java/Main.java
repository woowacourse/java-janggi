import controller.JanggiController;
import global.config.DiConfig;
import service.JanggiService;

public class Main {
    public static void main(String[] args) {
        DiConfig diConfig = new DiConfig();
        JanggiController janggiController = diConfig.janggiController();
        janggiController.run();
    }
}
