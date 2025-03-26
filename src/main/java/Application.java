import manager.GameManager;
import view.InputView;

public class Application {

    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        String gameRoomName = InputView.inputGameRoomName();
        gameManager.startGame(gameRoomName);
    }
}
