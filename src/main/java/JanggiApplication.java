import controller.JanggiController;
import db.connector.MySqlConnector;
import db.repository.GameRepository;
import db.repository.PieceRepository;

public class JanggiApplication {

    public static void main(String[] args) {
        GameRepository gameRepository = new GameRepository(
                new MySqlConnector(),
                new PieceRepository()
        );
        JanggiController controller = new JanggiController(gameRepository);

        controller.run();
    }
}
