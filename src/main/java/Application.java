import domain.JanggiManager;
import domain.JanggiRunner;
import util.MysqlConnectionFactory;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {

        JanggiRunner janggiRunner = new JanggiRunner(new InputView(), new OutputView(), new JanggiManager(
                new MysqlConnectionFactory()));
        janggiRunner.run();
    }
}
