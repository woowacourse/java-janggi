import controller.JanggiController;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(new OutputView());

        janggiController.run();
    }
}
