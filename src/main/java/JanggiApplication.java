import controller.JanggiController;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        new JanggiController(
                new InputView(),
                new OutputView()
        ).run();
    }
}
