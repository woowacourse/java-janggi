import application.GameReplayer;
import application.GameSessionService;
import io.GameConsole;
import io.InputView;
import io.OutputView;
import java.nio.file.Path;
import persistence.ConnectionProvider;
import persistence.JdbcGameSessionRepository;

public class Application {
    public static void main(String[] args) {
        GameConsole gameConsole = new GameConsole(
                new GameSessionService(
                        new JdbcGameSessionRepository(ConnectionProvider.fromFile(Path.of("db", "janggi"))),
                        new GameReplayer()
                ),
                new OutputView(),
                new InputView()
        );
        gameConsole.run();
    }
}
