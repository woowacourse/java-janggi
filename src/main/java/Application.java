import controller.JanggiController;
import dao.EntityMapper;
import dao.JanggiGameDao;
import dao.JanggiTransactionManager;
import dao.MySqlConnector;
import dao.PieceDao;
import dao.TransactionManager;
import domain.game.JanggiGame;
import view.ConsoleView;
import view.InputView;
import view.OutputView;
import view.support.OutputSupporter;

public class Application {

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView(new InputView(), new OutputView(new OutputSupporter()));
        TransactionManager<JanggiGame> transactionManager = new JanggiTransactionManager(
                new MySqlConnector(),
                new JanggiGameDao(),
                new PieceDao(),
                new EntityMapper()
        );
        transactionManager.createTable();
        JanggiController janggiController = new JanggiController(consoleView, transactionManager);
        janggiController.start();
    }
}
