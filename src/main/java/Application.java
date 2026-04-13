import controller.GameController;
import controller.QuitGameException;
import data.JanggiMapper;
import data.JdbcGameDao;
import data.JdbcPieceDao;
import domain.Game;
import domain.board.Board;
import domain.player.Players;
import repository.GameRepository;
import repository.JdbcGameRepository;
import view.InputView;
import view.OutputView;

public class Application {
    private static final String DB_URL = "jdbc:h2:~/janggi;INIT=RUNSCRIPT FROM\n"
            + "  'src/main/resources/schema.sql'";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameController controller = new GameController(inputView, outputView);
        GameRepository gameRepository = new JdbcGameRepository(
                new JdbcGameDao(),
                new JdbcPieceDao(),
                new JanggiMapper(),
                DB_URL,
                DB_USERNAME,
                DB_PASSWORD
        );

        while (true) {
            try {
                outputView.printGameId();
                Long gameId = Long.parseLong(inputView.readLine());

                Game game;
                if (gameId == 0L) {
                    Players players = controller.getPlayer();
                    Board board = controller.getBoard();
                    game = new Game(players, board);
                    gameRepository.save(game);
                } else {
                    game = gameRepository.findById(gameId)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
                }

                controller.run(game, gameRepository);
                break;
            } catch (QuitGameException | IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }
}
