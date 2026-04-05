import controller.JanggiController;
import view.ConsolePieceAppearance;
import view.InputView;
import view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        JanggiController janggiController = JanggiController.of(new InputView(),
                new OutputView(new ConsolePieceAppearance()));
        janggiController.run();
    }
}
