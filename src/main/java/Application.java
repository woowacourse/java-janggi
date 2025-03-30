import domain.JanggiManager;
import domain.JanggiRunner;
import util.MysqlConnectionUtil;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiRunner janggiRunner = new JanggiRunner(new InputView(), new OutputView(), new JanggiManager(
                MysqlConnectionUtil.getConnection()));
        janggiRunner.run();
    }
}
