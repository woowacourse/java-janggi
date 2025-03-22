import janggi.JanggiGame;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {

        OutputView outputView = new OutputView();
        InputView inputView = new InputView();

        JanggiGame janggiGame = new JanggiGame(outputView, inputView);
        janggiGame.startGame();
    }

}

