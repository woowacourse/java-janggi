import repository.DBConnectionUtil;
import repository.GameJdbcDao;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        DBConnectionUtil.initializeSchema();

        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), new GameJdbcDao());
        janggiController.run();
    }
}
