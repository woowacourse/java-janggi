import dao.GameRoomDao;
import dao.PieceDao;
import dao.init.ConnectionGenerator;
import dao.init.DatabaseSetting;
import manager.GameManager;
import manager.GameService;

public class Application {

    public static void main(String[] args) {
        DatabaseSetting.settingTable(ConnectionGenerator.getConnection());

        GameManager gameManager = new GameManager(
                new GameService(
                        new GameRoomDao(),
                        new PieceDao()
                )
        );
        gameManager.startGame();
    }
}
