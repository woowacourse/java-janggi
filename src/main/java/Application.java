import repository.JanggiRepository;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiRepository janggiRepository = new JanggiRepository();

        JanggiController janggiController = new JanggiController(inputView, outputView, janggiRepository);
        janggiController.run();
    }
}
