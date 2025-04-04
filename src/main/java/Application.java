import dao.JanggiDao;
import service.JanggiService;

public class Application {
    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController();
        JanggiService janggiService = new JanggiService(new JanggiDao());
        janggiController.run(janggiService);
    }
}
