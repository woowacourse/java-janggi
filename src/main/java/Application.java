import controller.JanggiController;
import persistence.mapper.EntityMapper;
import persistence.dao.JanggiGameDao;
import persistence.JanggiPersistenceManager;
import persistence.transaction.TransactionManager;
import persistence.connector.MySqlConnector;
import persistence.dao.PieceDao;
import view.ConsoleView;
import view.InputView;
import view.OutputView;
import view.support.OutputSupporter;

public class Application {

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView(new InputView(), new OutputView(new OutputSupporter()));
        JanggiPersistenceManager janggiPersistenceManager = new JanggiPersistenceManager(
                new TransactionManager(new MySqlConnector()),
                new JanggiGameDao(),
                new PieceDao(),
                new EntityMapper()
        );
        JanggiController janggiController = new JanggiController(consoleView, janggiPersistenceManager);
        janggiController.start();
    }
}
