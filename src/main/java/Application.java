import janggi.JanggiGame;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {

        final OutputView outputView = new OutputView();
        final InputView inputView = new InputView();

        final JanggiGame janggiGame = new JanggiGame(outputView, inputView);

        janggiGame.startGame();
    }

}

