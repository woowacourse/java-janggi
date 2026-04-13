import config.DatabaseInitializer;
import controller.GameController;
import model.board.BoardInitializer;
import repository.JdbcGameRepository;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        DatabaseInitializer.initialize();

        GameController gameController = new GameController(
                new BoardInitializer(),
                new JdbcGameRepository()
        );
        gameController.start();
    }
}
