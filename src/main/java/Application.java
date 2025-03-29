import controller.JanggiController;
import dao.GameDao;
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
        GameService gameService = new GameService(new GameDao());
        PieceService pieceService = new PieceService(new PieceDao());
        JanggiController janggiController = new JanggiController(consoleView, gameService, pieceService);
        janggiController.start();
    }
}
