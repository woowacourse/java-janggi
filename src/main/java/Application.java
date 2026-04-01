import controller.JanggiController;
import domain.Board;
import domain.JanggiGame;
import factory.JanggiBoardFactory;

public class Application {

    public static void main(String[] args) {
        JanggiBoardFactory janggiBoardFactory = new JanggiBoardFactory();
        JanggiGame janggiGame = JanggiGame.of(janggiBoardFactory.initialBoard());
        JanggiController janggiController = new JanggiController(janggiGame);

        janggiController.run();
    }
}