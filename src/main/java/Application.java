import controller.KoreaChessController;
import dao.GameDaoImpl;
import dao.PieceDaoImpl;
import dao.PlayerDaoImpl;
import dao.connect.ConnectionProvider;
import dao.connect.JanggiConnectionProvider;
import java.util.Scanner;
import service.GameInitializerService;
import service.GameLoadService;
import service.GameService;
import service.PieceService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView(new Scanner(System.in));

        ConnectionProvider connectionProvider = new JanggiConnectionProvider();
        GameDaoImpl gameRepository = new GameDaoImpl(connectionProvider);
        PlayerDaoImpl playerRepository = new PlayerDaoImpl(connectionProvider);
        PieceDaoImpl pieceRepository = new PieceDaoImpl(connectionProvider);

        try {
            KoreaChessController koreaChess = new KoreaChessController(
                    outputView,
                    inputView,
                    new GameInitializerService(outputView, inputView, gameRepository, playerRepository,
                            pieceRepository),
                    new GameLoadService(playerRepository, pieceRepository),
                    new GameService(gameRepository),
                    new PieceService(pieceRepository)
            );

            koreaChess.run();
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }
}
