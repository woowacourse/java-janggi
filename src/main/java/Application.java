import controller.KoreaChessController;
import db.ConnectionProvider;
import db.JanggiConnectionProvider;
import java.util.Scanner;
import repository.GameRepositoryImpl;
import repository.PieceRepositoryImpl;
import repository.PlayerRepositoryImpl;
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
        GameRepositoryImpl gameRepository = new GameRepositoryImpl(connectionProvider);
        PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl(connectionProvider);
        PieceRepositoryImpl pieceRepository = new PieceRepositoryImpl(connectionProvider);

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
