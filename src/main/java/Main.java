import config.DataSourceConfig;
import repository.BoardRepository;
import janggigame.JanggiGame;
import repository.JanggiGameRepository;
import util.SchemaInitializer;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSourceConfig.dataSource();
        SchemaInitializer.initialize(dataSource);
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(dataSource);
        BoardRepository boardRepository = new BoardRepository(dataSource);
        JanggiGame janggiGame = new JanggiGame(janggiGameRepository, boardRepository);
        janggiGame.run();
    }
}
