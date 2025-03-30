import application.JanggiGame;
import infrastructure.BoardJdbcRepository;
import infrastructure.MysqlConnector;
import infrastructure.TurnJdbcRepository;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        MysqlConnector mysqlConnector = new MysqlConnector();

        JanggiGame janggiGame = new JanggiGame(
                new InputView(), new OutputView(),
                new BoardJdbcRepository(mysqlConnector), new TurnJdbcRepository(mysqlConnector)
        );
        janggiGame.play();
    }
}
