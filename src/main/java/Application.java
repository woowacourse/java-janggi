import config.JanggiConfig;
import controller.JanggiController;

public class Application {
    public static void main(String[] args) {
        JanggiController janggiController = JanggiConfig.setupController();
        janggiController.run();
    }
}
