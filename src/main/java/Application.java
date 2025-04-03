import dao.gameroom.GameRoomCommandDaoImpl;
import dao.gameroom.GameRoomQueryDaoImpl;
import dao.init.MySQLConnectionGenerator;
import dao.piece.PieceCommandDaoImpl;
import dao.piece.PieceQueryDaoImpl;
import manager.GameManager;
import service.GameService;

public class Application {

    public static void main(String[] args) {
        GameManager gameManager = new GameManager(
                new GameService(
                        new PieceQueryDaoImpl(),
                        new PieceCommandDaoImpl(),
                        new GameRoomQueryDaoImpl(),
                        new GameRoomCommandDaoImpl(),
                        new MySQLConnectionGenerator()
                )
        );
        gameManager.startGame();
    }
}
