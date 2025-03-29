import dao.GameRoomDao;
import dao.PieceDao;
import dao.init.ConnectionFactory;
import dao.init.MySQLConnectionFactory;
import manager.GameManager;
import service.GameService;

public class Application {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new MySQLConnectionFactory();

        GameManager gameManager = new GameManager(
                new GameService(
                        new GameRoomDao(),
                        new PieceDao(),
                        connectionFactory
                )
        );
        gameManager.startGame();
    }
}
