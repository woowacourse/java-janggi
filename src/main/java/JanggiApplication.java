import janggi.game.Game;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final Game game = new Game(inputView, outputView);
        game.play();
    }
}
