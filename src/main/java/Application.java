import dao.PieceDao;
import java.sql.SQLException;
import repository.DockerRepository;
import repository.JanggiRepository;
import repository.MemoryRepository;
import view.ConfigurationOutputView;
import view.InputView;
import view.OutputView;

public class Application {

    private static final ConfigurationOutputView CONFIGURATION_OUTPUT_VIEW = new ConfigurationOutputView();

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiRepository repository = decideRepository();

        Game game = new Game(inputView, outputView, repository);
        game.start();
    }

    private static JanggiRepository decideRepository() {
        final var pieceDao = new PieceDao();
        try (final var connection = pieceDao.getConnection()) {
            System.out.println("connection = " + connection);
            return new DockerRepository(pieceDao);

        } catch (RuntimeException | SQLException e) {
            CONFIGURATION_OUTPUT_VIEW.printLocalGame();
            return new MemoryRepository();
        }
    }
}
