package janggi;

import javax.sql.DataSource;
import janggi.config.DataSourceConfig;
import janggi.repository.GameRoomRepository;
import janggi.repository.PieceRepository;
import janggi.service.GameService;

public class JanggiApplication {

    public static void main(String[] args) {
        DataSource dataSource = initDatabase();
        GameService gameService = initGameService(dataSource);

        JanggiGame game = new JanggiGame(gameService);
        game.run();
    }

    private static DataSource initDatabase() {
        DataSource dataSource = DataSourceConfig.getDataSource();
        DataSourceConfig.DatabaseInitializer.initialize(dataSource);
        return dataSource;
    }

    private static GameService initGameService(DataSource dataSource) {
        PieceRepository pieceRepository = new PieceRepository(dataSource);
        GameRoomRepository gameRoomRepository = new GameRoomRepository(dataSource);

        return new GameService(gameRoomRepository, pieceRepository);
    }
}
