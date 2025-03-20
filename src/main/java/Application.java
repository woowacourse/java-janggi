import domain.JanggiRunner;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiRunner janggiRunner = new JanggiRunner(new InputView(),new OutputView());
        janggiRunner.run();
    }
}
