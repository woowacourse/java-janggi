import domain.JanggiRunner;
import domain.JanggiManager;
import util.DBConnectionUtil;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiRunner janggiRunner = new JanggiRunner(new InputView(), new OutputView(), new JanggiManager(
                DBConnectionUtil.getConnection()));
        janggiRunner.run();
    }
}
