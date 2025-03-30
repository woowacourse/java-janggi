import controller.JanggiController;
import dao.BoardDaoImpl;
import dao.TurnDaoImpl;
import db.DatabaseConnector;
import db.ProductionDatabaseConnector;
import service.JanggiDaoService;

public class Application {

    public static void main(String[] args) {
        final DatabaseConnector databaseConnector = new ProductionDatabaseConnector();
        final JanggiDaoService janggiDaoService = new JanggiDaoService(
                new TurnDaoImpl(databaseConnector.getConnection()),
                new BoardDaoImpl(databaseConnector.getConnection())
        );
        final JanggiController janggiController = new JanggiController(janggiDaoService);
        janggiController.run();
    }
}
