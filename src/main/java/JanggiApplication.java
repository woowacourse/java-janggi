import domain.Game;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final Game game = new Game(inputView, outputView);

        while (!game.isEnd()) {
            try {
                game.doTurn();
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }
}
