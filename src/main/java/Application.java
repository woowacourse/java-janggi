import config.AppConfig;
import controller.JanggiController;

public class Application {
    public static void main(String[] args) {
        JanggiController janggiController = AppConfig.getInstance().janggiController();
        janggiController.start();
    }
}
