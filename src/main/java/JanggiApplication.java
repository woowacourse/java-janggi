import controller.JanggiController;
import view.InputView;

public class JanggiApplication {
    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(new InputView());
        janggiController.run();

    }
}
