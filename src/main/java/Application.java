import controller.JanggiController;
import domain.board.JanggiBoard;
import domain.board.JanggiGenerator;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        JanggiController janggiController = new JanggiController(inputView, outputView);
        janggiController.run();

    }

}
