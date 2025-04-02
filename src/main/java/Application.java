import application.Janggi;
import infrastructure.dao.GameDao;
import infrastructure.dao.MysqlConnector;
import infrastructure.dao.PieceDao;
import infrastructure.repository.GameRepositoryImpl;
import infrastructure.repository.PieceRepositoryImpl;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        MysqlConnector mysqlConnector = new MysqlConnector();

        Janggi janggi = new Janggi(
                new InputView(),
                new OutputView(),
                new PieceRepositoryImpl(new PieceDao(mysqlConnector)),
                new GameRepositoryImpl(new GameDao(mysqlConnector))
        );
        janggi.play();
    }
}
