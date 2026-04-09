import db.dao.JdbcBoardPieceDao;
import db.dao.JdbcGameDao;
import db.dao.JdbcMoveHistoryDao;
import db.jdbc.ConnectionManager;
import db.jdbc.DatabaseMigrator;
import db.jdbc.FlywayDatabaseMigrator;
import db.jdbc.ProductionConnectionManager;
import db.repository.JanggiGameRepository;
import db.repository.JdbcJanggiGameRepository;
import service.DbTemplate;
import service.GamePlayService;
import service.GamePrepareService;
import service.MoveHistoryShowService;
import service.PreparedGame;
import view.JanggiView;
import view.ServiceMenu;

public class Application {

    public static void main(String[] args) {
        final ConnectionManager connectionManager = new ProductionConnectionManager();
        migrate(connectionManager);

        doService(
            new JanggiView(),
            new DbTemplate(connectionManager),
            getRepository());
    }

    private static void migrate(final ConnectionManager connectionManager) {
        final DatabaseMigrator migrator = new FlywayDatabaseMigrator(connectionManager);
        migrator.migrate();
    }

    private static JanggiGameRepository getRepository() {
        return new JdbcJanggiGameRepository(
            new JdbcGameDao(),
            new JdbcBoardPieceDao(),
            new JdbcMoveHistoryDao()
        );
    }

    private static void doService(
        final JanggiView view,
        final DbTemplate dbTemplate,
        final JanggiGameRepository repository
    ) {
        ServiceMenu menu = view.askServiceMenu();
        if (menu.isShowMoveHistory()) {
            new MoveHistoryShowService(view, dbTemplate, repository).show();
            return;
        }
        PreparedGame prepared = new GamePrepareService(view, dbTemplate, repository).prepare();
        new GamePlayService(view, dbTemplate, repository).play(prepared);
    }
}
