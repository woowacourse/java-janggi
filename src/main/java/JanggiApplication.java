import config.DataSourceFactory;
import config.DatabaseInitializer;
import controller.JanggiController;
import javax.sql.DataSource;
import repository.JdbcJanggiGameRepository;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        DataSource dataSource = DataSourceFactory.create();
        DatabaseInitializer.initialize(dataSource);

        new JanggiController(
                new InputView(),
                new OutputView(),
                new JdbcJanggiGameRepository(dataSource)
        ).run();
    }
}
