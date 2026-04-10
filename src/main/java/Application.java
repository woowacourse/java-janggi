import application.GameManager;
import application.GameSessionHandler;
import java.util.Scanner;
import persistence.ConnectionFactory;
import persistence.JdbcGameRepository;
import persistence.SchemaInitializer;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = ConnectionFactory.defaultFactory();
        new SchemaInitializer(connectionFactory).initialize();
        JdbcGameRepository gameRepository = new JdbcGameRepository(connectionFactory);
        GameSessionHandler gameSessionHandler = new GameSessionHandler(gameRepository);

        GameManager gameManager = new GameManager(
                new InputView(new Scanner(System.in)),
                new OutputView(),
                gameSessionHandler
        );
        gameManager.play();
    }
}
