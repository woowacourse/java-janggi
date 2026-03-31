import controller.GameController;
import model.board.BoardInitializer;

public class Application {
    public static void main(String[] args) {
        GameController gameController = new GameController(new BoardInitializer());
        gameController.start();
    }
}
