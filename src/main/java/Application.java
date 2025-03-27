import game.controller.JanggiController;
import game.view.ConsoleView;
import game.view.InputView;
import game.view.OutputView;
import game.view.support.OutputSupporter;

public class Application {

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView(new InputView(), new OutputView(new OutputSupporter()));
        JanggiController janggiController = new JanggiController(consoleView);
        janggiController.start();
    }
}
