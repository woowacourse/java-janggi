import controller.JanggiController;
import dao.GameDao;
import dao.JdbcConnection;
import dao.PieceDao;
import view.ConsoleView;
import view.InputView;
import view.OutputView;
import view.support.OutputSupporter;

public class Application {

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView(new InputView(), new OutputView(new OutputSupporter()));
        JdbcConnection jdbcConnection = new JdbcConnection();
        GameDao gameDao = new GameDao(jdbcConnection);
        PieceDao pieceDao = new PieceDao(jdbcConnection);
        JanggiController janggiController = new JanggiController(consoleView, gameDao, pieceDao);
        janggiController.start();
    }
}
