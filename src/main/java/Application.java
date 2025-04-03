import janggi.JanggiGame;
import janggi.dao.GameDaoImpl;
import janggi.dao.PieceDaoImpl;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(final String[] args) {

        final OutputView outputView = new OutputView();
        final InputView inputView = new InputView();
        final JanggiService janggiService = new JanggiService(new GameDaoImpl(), new PieceDaoImpl());
        final JanggiGame janggiGame = new JanggiGame(outputView, inputView, janggiService);

        janggiGame.startGame();
    }

}

