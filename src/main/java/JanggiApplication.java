import db.DbBootstrap;
import view.InputView;
import view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        DbBootstrap.initialize();

        JanggiGameRunner janggiGame = new JanggiGameRunner(new InputView(), new OutputView());
        janggiGame.run();
    }
}
