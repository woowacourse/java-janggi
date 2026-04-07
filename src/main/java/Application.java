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
        final ConnectionManager connectionManager = getConnectionManager();
        final JanggiGameRepository repository = getRepository(connectionManager);
        final JanggiView view = new JanggiView();

        final GameSession session = new GamePreparationService(view, repository).prepare();
        session.run();
    }

    private static JanggiGameRepository getRepository(final ConnectionManager connectionManager) {
        final GameDao gameDao = new JdbcGameDao(connectionManager);
        final BoardPieceDao boardPieceDao = new JdbcBoardPieceDao(connectionManager);
        return new JdbcJanggiGameRepository(gameDao, boardPieceDao);
    }

    private static ProductionConnectionManager getConnectionManager() {
        final ProductionConnectionManager connectionManager = new ProductionConnectionManager();
        final DatabaseMigrator databaseMigrator = new DatabaseMigrator(connectionManager);
        databaseMigrator.initialize();
        return connectionManager;
    }
}
