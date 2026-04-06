import core.GamePreparationService;
import core.GameSession;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.jdbc.ConnectionManager;
import db.jdbc.DatabaseMigrator;
import db.jdbc.JdbcBoardPieceDao;
import db.jdbc.JdbcGameDao;
import db.jdbc.ProductionConnectionManager;
import db.repository.JanggiGameRepository;
import db.repository.JdbcJanggiGameRepository;
import view.JanggiView;

public class Application {

    public static void main(String[] args) {
        ConnectionManager connectionManager = getConnectionManager();
        JanggiGameRepository repository = getRepository(connectionManager);
        JanggiView view = new JanggiView();

        GameSession session = new GamePreparationService(view, repository).prepare();
        session.run();
    }

    private static JanggiGameRepository getRepository(ConnectionManager connectionManager) {
        GameDao gameDao = new JdbcGameDao(connectionManager);
        BoardPieceDao boardPieceDao = new JdbcBoardPieceDao(connectionManager);
        return new JdbcJanggiGameRepository(gameDao, boardPieceDao);
    }

    private static ProductionConnectionManager getConnectionManager() {
        ProductionConnectionManager connectionManager = new ProductionConnectionManager();
        DatabaseMigrator databaseMigrator = new DatabaseMigrator(connectionManager);
        databaseMigrator.initialize();
        return connectionManager;
    }
}
