import controller.GameController;
import controller.QuitGameException;
import data.JanggiMapper;
import data.JdbcGameDao;
import data.JdbcBoardDao;
import domain.Game;
import domain.board.Board;
import domain.player.Players;
import repository.GameRepository;
import repository.JdbcGameRepository;
import service.GameService;
import view.InputView;
import view.OutputView;

public class Application {
    private static final String DB_URL = "jdbc:h2:~/janggi_new;INIT=RUNSCRIPT FROM 'file:src/main/resources/schema.sql'";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameRepository gameRepository = new JdbcGameRepository(
                new JdbcGameDao(),
                new JdbcBoardDao(),
                new JanggiMapper(),
                DB_URL,
                DB_USERNAME,
                DB_PASSWORD
        );
        GameService gameService = new GameService(gameRepository);
        GameController controller = new GameController(inputView, outputView, gameService);

        while (true) {
            try {
                outputView.printGameId();
                Long gameId = Long.parseLong(inputView.readLine());

                Game game;
                if (gameId == 0L) {
                    Players players = controller.getPlayer();
                    Board board = controller.getBoard();
                    game = gameService.startNewGame(players, board);
                } else {
                    game = gameService.loadGame(gameId);
                }

                controller.run(game.id());
                break;
            } catch (QuitGameException | IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }
}
