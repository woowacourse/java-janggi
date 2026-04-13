import controller.JanggiController;
import dao.mongodb.BoardDao;
import dao.mongodb.MongoConnection;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        try (MongoConnection connection = new MongoConnection()) {
            JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), new BoardDao(connection));
            janggiController.run();
        }
    }
}