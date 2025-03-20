import controller.JanggiController;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final JanggiController controller = new JanggiController(inputView, outputView);
        controller.run();
    }
}
