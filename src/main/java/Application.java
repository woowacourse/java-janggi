import application.JanggiGame;
import infrastructure.MysqlConnector;
import infrastructure.TurnJdbcRepository;
import infrastructure.dao.BoardDao;
import infrastructure.repository.BoardJdbcRepository;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        MysqlConnector mysqlConnector = new MysqlConnector();

        JanggiGame janggiGame = new JanggiGame(
                new InputView(), new OutputView(),
                new BoardJdbcRepository(new BoardDao(mysqlConnector)),
                new TurnJdbcRepository(mysqlConnector)
        );
        janggiGame.play();
    }
}
