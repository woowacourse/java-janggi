import dao.init.ConnectionGenerator;
import dao.init.DatabaseSetting;
import manager.GameManager;
import view.InputView;

public class Application {

    public static void main(String[] args) {
        DatabaseSetting.settingTable(ConnectionGenerator.getConnection());

        GameManager gameManager = new GameManager();
        String gameRoomName = InputView.inputGameRoomName();
        gameManager.startGame(gameRoomName);
    }
}
