import controller.JanggiController;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        JanggiController controller = new JanggiController(inputView, outputView);
        controller.run();
    }
}
