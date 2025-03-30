import dao.GameRoomDao;
import dao.PieceDao;
import dao.init.MySQLConnectionGenerator;
import manager.GameManager;
import service.GameService;

public class Application {

    public static void main(String[] args) {
        GameManager gameManager = new GameManager(
                new GameService(
                        new GameRoomDao(),
                        new PieceDao(),
                        new MySQLConnectionGenerator()
                )
        );
        gameManager.startGame();
    }
}
