import core.GamePlayService;
import core.GamePreparationService;
import core.PreparedGame;
import core.ShowMoveHistoryService;
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
import view.JanggiView;
import view.ServiceMenu;

public class Application {

    public static void main(String[] args) {
        final ConnectionManager connectionManager = getConnectionManager();
        migrate(connectionManager);
        final JanggiGameRepository repository = getRepository();
        final JanggiView view = new JanggiView();

        ServiceMenu menu = view.askServiceMenu();
        if (menu.isShowMoveHistory()) {
            showMoveHistory(view, repository, connectionManager);
            return;
        }
        playGame(view, repository, connectionManager);
    }

    private static void showMoveHistory(
        final JanggiView view,
        final JanggiGameRepository repository,
        final ConnectionManager connectionManager
    ) {
        final ShowMoveHistoryService showMoveHistoryService = new ShowMoveHistoryService(
            view,
            repository,
            connectionManager
        );
        showMoveHistoryService.show();
    }

    private static void playGame(
        final JanggiView view,
        final JanggiGameRepository repository,
        final ConnectionManager connectionManager
    ) {
        final GamePreparationService preparationService = new GamePreparationService(
            view,
            repository,
            connectionManager
        );
        final GamePlayService playService = new GamePlayService(connectionManager, repository);

        PreparedGame preparedGame = preparationService.prepare();
        playService.run(preparedGame.gameId(), preparedGame.session());
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
