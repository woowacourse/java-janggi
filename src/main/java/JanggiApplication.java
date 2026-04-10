import controller.JanggiController;
import dao.BoardPieceDao;
import dao.GameRoomDao;
import db.ConnectionManager;
import db.DatabaseInitializer;
import repository.GameRepository;
import service.JanggiGameService;
import view.InputView;
import view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        ConnectionManager connectionManager = new ConnectionManager();
        new DatabaseInitializer(connectionManager).initialize();

        GameRoomDao gameRoomDao = new GameRoomDao(connectionManager);
        BoardPieceDao boardPieceDao = new BoardPieceDao(connectionManager);
        GameRepository gameRepository = new GameRepository(gameRoomDao, boardPieceDao);
        JanggiGameService gameService = new JanggiGameService(gameRepository);

        JanggiController controller = new JanggiController(new InputView(), new OutputView(), gameService);
        controller.run();
    }
}
