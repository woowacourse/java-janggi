import db.DbBootstrap;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        DbBootstrap.initialize();

        Runner janggiGame = new Runner(new InputView(), new OutputView());
        janggiGame.run();
    }
}
