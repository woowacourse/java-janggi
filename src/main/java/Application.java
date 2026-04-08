import application.GameManager;
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

        GameManager gameManager = new GameManager(
                new InputView(new Scanner(System.in)),
                new OutputView(),
                new JdbcGameRepository(connectionFactory)
        );
        gameManager.play();
    }
}
