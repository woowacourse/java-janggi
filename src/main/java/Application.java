import janggi.Janggi;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        new Janggi(
                new InputView(),
                new OutputView()
        ).play();

    }
}
