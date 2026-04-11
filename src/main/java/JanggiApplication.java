import config.DataSourceFactory;
import config.DatabaseInitializer;
import controller.JanggiController;
import javax.sql.DataSource;
import repository.JdbcGameDao;
import repository.JdbcJanggiGameRepository;
import repository.JdbcPieceDao;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        DataSource dataSource = DataSourceFactory.create();
        DatabaseInitializer.initialize(dataSource);

        JdbcPieceDao jdbcPieceDao = new JdbcPieceDao();
        JdbcGameDao jdbcGameDao = new JdbcGameDao();
        JdbcJanggiGameRepository janggiGameRepository = new JdbcJanggiGameRepository(dataSource, jdbcGameDao, jdbcPieceDao);

        new JanggiController(
                new InputView(),
                new OutputView(),
                janggiGameRepository
        ).run();
    }
}
