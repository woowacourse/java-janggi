import janggi.JanggiGame;
import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(final String[] args) {

        final OutputView outputView = new OutputView();
        final InputView inputView = new InputView();

        final GameDao gameDao = new GameDao();
        final PieceDao pieceDao = new PieceDao();

        final JanggiGame janggiGame = new JanggiGame(outputView, inputView, gameDao, pieceDao);

        janggiGame.startGame();
    }

}

