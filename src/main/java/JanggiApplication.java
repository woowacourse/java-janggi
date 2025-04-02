import game.JanggiGame;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        JanggiGame janggiGame = new JanggiGame(inputView, outputView);
        janggiGame.run();
    }
}
