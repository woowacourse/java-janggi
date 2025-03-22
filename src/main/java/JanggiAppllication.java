import Controller.Controller;
import view.InputView;
import view.OutputView;

public class JanggiAppllication {
    public static void main(String[] args) {
        Controller controller = new Controller(new InputView(), new OutputView());
        controller.run();
    }
}
