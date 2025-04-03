import application.JanggiUseCase;
import infrastructure.dao.GameDao;
import infrastructure.dao.MysqlConnector;
import infrastructure.dao.PieceDao;
import infrastructure.repository.GameRepositoryImpl;
import infrastructure.repository.PieceRepositoryImpl;
import view.InputView;
import view.JanggiConsole;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        MysqlConnector mysqlConnector = new MysqlConnector();

        JanggiConsole janggiConsole = new JanggiConsole(
                new InputView(),
                new OutputView(),
                new JanggiUseCase(
                        new PieceRepositoryImpl(new PieceDao(mysqlConnector)),
                        new GameRepositoryImpl(new GameDao(mysqlConnector))
                )
        );
        janggiConsole.play();
    }
}
