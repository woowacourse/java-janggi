package janggi;

import javax.sql.DataSource;
import janggi.config.DataSourceConfig;
import janggi.repository.GameRepository;
import janggi.repository.PieceRepository;

public class JanggiApplication {
    public static void main(String[] args) {
        DataSource dataSource = DataSourceConfig.getDataSource();
        DatabaseInitializer.initialize(dataSource);

        GameRepository gameRepository = new GameRepository(dataSource);
        PieceRepository pieceRepository = new PieceRepository(dataSource);
        JanggiGame game = new JanggiGame(gameRepository, pieceRepository);

        game.run();
    }
}
