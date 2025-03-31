import config.MySqlConnector;
import controller.JanggiController;
import dao.PieceDao;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        final MySqlConnector mySqlConnector = new MySqlConnector();
        final JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), new PieceDao(mySqlConnector));
        janggiController.run();
    }
}
