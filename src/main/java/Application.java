import controller.JanggiController;
import repository.DbJanggiRepository;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiService janggiService = new JanggiService(new DbJanggiRepository());
        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), janggiService);
        janggiController.run();
    }
}
