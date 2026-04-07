import controller.JanggiController;
import infrastructure.DatabaseInitializer;
import infrastructure.JdbcConnectionManager;
import repository.JdbcJanggiRepository;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args) {
        JdbcConnectionManager jdbcConnectionManager = new JdbcConnectionManager("jdbc:mysql://localhost:3306/janggi",
                "root", "0502");
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(jdbcConnectionManager);
        databaseInitializer.init();

        JanggiController janggiController = new JanggiController(
                new InputView(),
                new OutputView(),
                new JanggiService(new JdbcJanggiRepository(jdbcConnectionManager))
        );
        janggiController.run();
    }
}
