import repository.DBConnectionUtil;
import repository.GameJdbcDao;
import repository.BoardJdbcDao;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        DBConnectionUtil.initializeSchema();

        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), new JanggiService(new GameJdbcDao(), new BoardJdbcDao()));
        janggiController.run();
    }
}
