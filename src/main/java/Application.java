import dao.gameroom.GameRoomDaoImpl;
import dao.init.MySQLConnectionGenerator;
import dao.piece.PieceDaoImpl;
import manager.GameManager;
import service.GameService;

public class Application {

    public static void main(String[] args) {
        GameManager gameManager = new GameManager(
                new GameService(
                        new PieceDaoImpl(),
                        new GameRoomDaoImpl(),
                        new MySQLConnectionGenerator()
                )
        );
        gameManager.startGame();
    }
}