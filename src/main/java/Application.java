import board.SangSetup;
import core.JanggiGame;
import view.DisplayBoard;
import view.InputView;
import view.JanggiView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiView view = new JanggiView(new InputView(), new OutputView());
        SangSetup choSangSetupType = view.askChoSangSetup();
        SangSetup hanSangSetupType = view.askHanSangSetup();
        JanggiGame game = JanggiGame.of(choSangSetupType, hanSangSetupType);

        new Application(game, view).run();
    }

    private final JanggiGame game;
    private final JanggiView view;

    public Application(JanggiGame game, JanggiView view) {
        this.game = game;
        this.view = view;
    }

    public void run() {
        boolean notJangGun = true;
        while (notJangGun) {
            view.printBoard(DisplayBoard.of(game.getBoard()));
            notJangGun = false;
        }
    }
}
