import core.GamePreparationService;
import core.GameSession;
import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.jdbc.ConnectionManager;
import db.jdbc.DatabaseMigrator;
import db.jdbc.FlywayDatabaseMigrator;
import db.dao.JdbcBoardPieceDao;
import db.dao.JdbcGameDao;
import db.jdbc.ProductionConnectionManager;
import db.repository.JanggiGameRepository;
import db.repository.JdbcJanggiGameRepository;
import view.JanggiView;

public class Application {

    public static void main(String[] args) {
        final ConnectionManager connectionManager = getConnectionManager();
        migrate(connectionManager);
        final JanggiGameRepository repository = getRepository(connectionManager);
        final JanggiView view = new JanggiView();

        final GameSession session = new GamePreparationService(view, repository).prepare();
        session.run();
    }

    private static ConnectionManager getConnectionManager() {
        return new ProductionConnectionManager();
    }

    private static void migrate(final ConnectionManager connectionManager) {
        final DatabaseMigrator migrator = new FlywayDatabaseMigrator(connectionManager);
        migrator.migrate();
    }

    private static JanggiGameRepository getRepository(final ConnectionManager connectionManager) {
        final GameDao gameDao = new JdbcGameDao();
        final BoardPieceDao boardPieceDao = new JdbcBoardPieceDao();
        return new JdbcJanggiGameRepository(gameDao, boardPieceDao, connectionManager);
    }
}
