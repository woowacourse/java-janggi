import controller.JanggiController;
import dao.GameDao;
import dao.JdbcConnection;
import dao.PieceDao;
import service.GameService;
import service.PieceService;
import view.ConsoleView;
import view.InputView;
import view.OutputView;
import view.support.OutputSupporter;

public class Application {

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView(new InputView(), new OutputView(new OutputSupporter()));
        JdbcConnection jdbcConnection = new JdbcConnection();
        GameService gameService = new GameService(new GameDao(jdbcConnection));
        PieceService pieceService = new PieceService(new PieceDao(jdbcConnection));
        JanggiController janggiController = new JanggiController(consoleView, gameService, pieceService);
        janggiController.start();
    }
}
