import controller.JanggiController;
import domain.GameStatus;
import domain.JanggiGame;
import factory.JanggiBoardFactory;

public class Application {

    public static void main(String[] args) {
        JanggiBoardFactory janggiBoardFactory = new JanggiBoardFactory();
        JanggiGame janggiGame = JanggiGame.of(janggiBoardFactory.initialBoard(), GameStatus.GREEN_PLAYER_TURN);
        JanggiController janggiController = new JanggiController(janggiGame);

        janggiController.run();
    }
}
