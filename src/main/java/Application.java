import domain.JanggiRunner;
import domain.JanggiTransactionManager;
import util.MysqlConnectionFactory;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {

        JanggiRunner janggiRunner = new JanggiRunner(new InputView(), new OutputView(), new JanggiTransactionManager(
                new MysqlConnectionFactory()));
        janggiRunner.run();
    }
}
