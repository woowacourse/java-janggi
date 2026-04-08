import controller.JanggiController;
import db.connector.MySqlConnector;
import db.repository.GameRepository;
import db.repository.PieceRepository;

public class JanggiApplication {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/janggi";
    private static final String DATABASE_USERNAME = "janggi";
    private static final String DATABASE_PASSWORD = "janggi";

    public static void main(String[] args) {
        GameRepository gameRepository = new GameRepository(
                new MySqlConnector(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD),
                new PieceRepository()
        );
        JanggiController controller = new JanggiController(gameRepository);

        controller.run();
    }
}
