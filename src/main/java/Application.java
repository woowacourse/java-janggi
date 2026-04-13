import controller.JanggiController;
import repository.JanggiRepository;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiRepository janggiRepository = new JanggiRepository();
        JanggiService janggiService = new JanggiService(janggiRepository);

        JanggiController janggiController = new JanggiController(inputView, outputView, janggiService);
        janggiController.run();
    }
}
