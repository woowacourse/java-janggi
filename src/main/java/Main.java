import global.config.DiConfig;
import service.JanggiService;

public class Main {
    public static void main(String[] args) {
        DiConfig diConfig = new DiConfig();
        JanggiService janggiService = diConfig.janggiService();
        janggiService.run();
    }
}
