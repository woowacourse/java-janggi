import controller.JanggiController;
import domain.Board;
import domain.JanggiGame;
import factory.JanggiBoardFactory;

public class Application {
    public static void main(String[] args) {
        JanggiBoardFactory janggiBoardFactory = new JanggiBoardFactory();
        Board board = Board.of(janggiBoardFactory.initialBoard());
        JanggiGame janggiGame = new JanggiGame(board);
        JanggiController janggiController = new JanggiController(janggiGame);

        janggiController.run();
    }
}