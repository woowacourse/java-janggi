import view.ConsoleView;
import view.InputView;
import view.OutputView;
import view.support.OutputSupporter;

public class Application {

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView(new InputView(), new OutputView(new OutputSupporter()));
        JanggiGame janggiGame = new JanggiGame(consoleView);
        janggiGame.start();
    }
}
