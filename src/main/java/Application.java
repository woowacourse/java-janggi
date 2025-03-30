import application.JanggiGame;
import infrastructure.MysqlConnector;
import infrastructure.dao.BoardDao;
import infrastructure.dao.TurnDao;
import infrastructure.repository.BoardJdbcRepository;
import infrastructure.repository.TurnJdbcRepository;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        MysqlConnector mysqlConnector = new MysqlConnector();

        JanggiGame janggiGame = new JanggiGame(
                new InputView(), new OutputView(),
                new BoardJdbcRepository(new BoardDao(mysqlConnector)),
                new TurnJdbcRepository(new TurnDao(mysqlConnector))
        );
        janggiGame.play();
    }
}
