import infrastructure.BoardRepository;
import infrastructure.DbConnection;
import infrastructure.TurnRepository;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();

        JanggiGame janggiGame = new JanggiGame(
                new InputView(), new OutputView(),
                new BoardRepository(dbConnection), new TurnRepository(dbConnection)
        );
        janggiGame.play();
    }
}
