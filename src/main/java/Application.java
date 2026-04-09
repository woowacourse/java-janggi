import db.dao.BoardPieceDao;
import db.dao.GameDao;
import db.dao.JdbcBoardPieceDao;
import db.dao.JdbcGameDao;
import db.dao.JdbcMoveHistoryDao;
import db.dao.MoveHistoryDao;
import db.jdbc.ConnectionManager;
import db.jdbc.DatabaseMigrator;
import db.jdbc.FlywayDatabaseMigrator;
import db.jdbc.ProductionConnectionManager;
import db.repository.JanggiGameRepository;
import db.repository.JdbcJanggiGameRepository;
import service.DbTemplate;
import service.GamePlayService;
import service.MoveHistoryShowService;
import view.JanggiView;
import view.ServiceMenu;

public class Application {

    public static void main(String[] args) {
        final ConnectionManager connectionManager = getConnectionManager();
        migrate(connectionManager);
        final JanggiGameRepository repository = getRepository();
        final JanggiView view = new JanggiView();
        final DbTemplate dbTemplate = new DbTemplate(connectionManager);

        ServiceMenu menu = view.askServiceMenu();
        if (menu.isShowMoveHistory()) {
            new MoveHistoryShowService(view, dbTemplate, repository).show();
            return;
        }
        new GamePlayService(view, dbTemplate, repository).play();
    }

    private static ConnectionManager getConnectionManager() {
        return new ProductionConnectionManager();
    }

    private static void migrate(final ConnectionManager connectionManager) {
        final DatabaseMigrator migrator = new FlywayDatabaseMigrator(connectionManager);
        migrator.migrate();
    }

    private static JanggiGameRepository getRepository() {
        final GameDao gameDao = new JdbcGameDao();
        final BoardPieceDao boardPieceDao = new JdbcBoardPieceDao();
        final MoveHistoryDao moveHistoryDao = new JdbcMoveHistoryDao();
        return new JdbcJanggiGameRepository(gameDao, boardPieceDao, moveHistoryDao);
    }
}
