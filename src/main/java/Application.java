import application.Janggi;
import infrastructure.MysqlConnector;
import infrastructure.dao.GameDao;
import infrastructure.dao.PieceDao;
import infrastructure.repository.GameJdbcRepository;
import infrastructure.repository.PieceJdbcRepository;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        MysqlConnector mysqlConnector = new MysqlConnector();

        Janggi janggi = new Janggi(
                new InputView(),
                new OutputView(),
                new PieceJdbcRepository(new PieceDao(mysqlConnector)),
                new GameJdbcRepository(new GameDao(mysqlConnector))
        );
        janggi.play();
    }
}
