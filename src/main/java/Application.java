import controller.JanggiController;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiController controller = new JanggiController(new InputView(), new OutputView());
        controller.run();
    }
}
