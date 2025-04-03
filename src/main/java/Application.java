import janggi.JanggiGame;
import janggi.database.dao.BoardDao;
import janggi.database.DatabaseInitializer;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        BoardDao boardDao = new BoardDao();
        JanggiGame janggiGame = new JanggiGame(inputView, outputView, boardDao);

        janggiGame.play();
    }
}
