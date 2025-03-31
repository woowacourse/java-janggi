import application.Janggi;
import infrastructure.MysqlConnector;
import infrastructure.dao.BoardDao;
import infrastructure.dao.GameDao;
import infrastructure.repository.BoardJdbcRepository;
import infrastructure.repository.GameJdbcRepository;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        MysqlConnector mysqlConnector = new MysqlConnector();

        Janggi janggi = new Janggi(
                new InputView(),
                new OutputView(),
                new BoardJdbcRepository(new BoardDao(mysqlConnector)),
                new GameJdbcRepository(new GameDao(mysqlConnector))
        );
        janggi.play();
    }
}
