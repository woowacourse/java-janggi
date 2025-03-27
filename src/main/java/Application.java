import controller.JanggiController;
import view.ConsoleView;
import view.InputView;
import view.OutputView;
import view.support.OutputSupporter;

public class Application {

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView(new InputView(), new OutputView(new OutputSupporter()));
        JanggiController janggiController = new JanggiController(consoleView);
        janggiController.start();
    }
}
