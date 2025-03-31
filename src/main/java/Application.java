import janggi.Janggi;
import janggi.service.JanggiService;
import janggi.repository.mysql.MysqlConnectionProvider;
import janggi.repository.mysql.GameMysqlRepository;
import janggi.repository.mysql.PieceMysqlRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {

    public static void main(final String[] args) throws SQLException {
        final Connection connection = new MysqlConnectionProvider().getConnection();

        new Janggi(
                new InputView(),
                new OutputView(),
                new JanggiService(
                        new GameMysqlRepository(connection),
                        new PieceMysqlRepository(connection)
                )
        ).run();

    }
}
