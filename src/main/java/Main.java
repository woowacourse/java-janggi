import config.DataSourceConfig;
import controller.JanggiGameController;
import repository.BoardRepository;
import repository.JanggiGameRepository;
import repository.jdbc.JdbcBoardRepository;
import repository.jdbc.JdbcJanggiGameRepository;
import service.JanggiGameService;
import util.SchemaInitializer;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSourceConfig.dataSource();
        SchemaInitializer.initialize(dataSource);
        JanggiGameRepository janggiGameRepository = new JdbcJanggiGameRepository(dataSource);
        BoardRepository jdbcBoardRepository = new JdbcBoardRepository(dataSource);
        JanggiGameService janggiGameService = new JanggiGameService(janggiGameRepository, jdbcBoardRepository, dataSource);
        JanggiGameController janggiGameController = new JanggiGameController(janggiGameService);

        janggiGameController.run();
    }
}
