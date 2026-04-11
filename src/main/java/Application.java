import controller.GameController;
import database.MysqlConnectionManager;

public class Application {
    public static void main(String[] args) {
        GameController gameController = new GameController(new MysqlConnectionManager());
        gameController.start();
    }
}
