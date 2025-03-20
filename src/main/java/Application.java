import view.ConsoleView;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView(new InputView(), new OutputView());
        JanggiGame janggiGame = new JanggiGame(consoleView);
        janggiGame.start();
    }
}
