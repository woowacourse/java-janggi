package janggi;

import janggi.config.ConnectionManager;
import janggi.config.SchemaInitializer;
import janggi.dao.BoardPieceDao;
import janggi.dao.GameRoomDao;
import janggi.dao.JdbcBoardPieceDao;
import janggi.dao.JdbcGameRoomDao;
import janggi.domain.PalaceTopology;
import janggi.factory.PieceFactory;
import janggi.mapper.GamePersistenceMapper;
import janggi.repository.GameRepository;
import janggi.repository.JdbcGameRepository;
import janggi.service.GamePersistenceService;

public class Application {
    public static void main(String[] args) {
        ConnectionManager connectionManager = new ConnectionManager();
        SchemaInitializer schemaInitializer = new SchemaInitializer(connectionManager);

        GameRoomDao gameRoomDao = new JdbcGameRoomDao();
        BoardPieceDao boardPieceDao = new JdbcBoardPieceDao();
        PieceFactory pieceFactory = new PieceFactory(PalaceTopology.from());
        GamePersistenceMapper gamePersistenceMapper = new GamePersistenceMapper(pieceFactory);

        GameRepository gameRepository = new JdbcGameRepository(gameRoomDao, boardPieceDao, gamePersistenceMapper);

        GamePersistenceService gamePersistenceService = new GamePersistenceService(connectionManager, schemaInitializer, gameRepository);
        Runner runner = new Runner(gamePersistenceService);
        runner.run();
    }
}