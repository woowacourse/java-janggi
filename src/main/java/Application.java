import janggi.Janggi;
import janggi.repository.mysql.GameMysqlRepository;
import janggi.repository.mysql.MysqlConnectionProvider;
import janggi.repository.mysql.PieceMysqlRepository;
import janggi.service.JanggiService;
import janggi.service.Transaction;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.sql.SQLException;

public class Application {

    public static void main(final String[] args) throws SQLException {

        new Janggi(
                new InputView(),
                new OutputView(),
                new JanggiService(
                        new MysqlConnectionProvider(),
                        new Transaction(),
                        new GameMysqlRepository(),
                        new PieceMysqlRepository()
                )
        ).run();

    }
}
