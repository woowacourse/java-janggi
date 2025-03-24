import game.JanggiGame;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiGame janggiGame = new JanggiGame(
                new InputView(),
                new OutputView()
        );
        janggiGame.run();
    }
}
