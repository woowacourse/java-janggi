import controller.JanggiController;
import domain.JanggiBoard;
import domain.boardgenerator.JanggiBoardGenerator;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(),
                new JanggiBoard(new JanggiBoardGenerator()));
        janggiController.run();
    }
}
