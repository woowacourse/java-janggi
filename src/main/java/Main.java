import config.DataSourceConfig;
import janggigame.JanggiGame;
import repository.BoardRepository;
import repository.JanggiGameRepository;
import service.JanggiGameService;
import util.SchemaInitializer;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSourceConfig.dataSource();
        SchemaInitializer.initialize(dataSource);
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(dataSource);
        BoardRepository boardRepository = new BoardRepository(dataSource);
        JanggiGameService janggiGameService = new JanggiGameService(janggiGameRepository, boardRepository, dataSource);
        JanggiGame janggiGame = new JanggiGame(janggiGameService);

        janggiGame.run();
    }
}
