import controller.JanggiController;
import domain.piece.ConsolePieceAppearance;
import view.InputView;
import view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(new InputView(),
                new OutputView(new ConsolePieceAppearance()));
        janggiController.run();
    }
}
