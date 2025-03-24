import domain.game.Game;
import domain.game.Janggi;
import view.InputView;
import view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        final Janggi janggi = new Janggi();
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final Game game = new Game(janggi, inputView, outputView);
        game.play();
    }
}
