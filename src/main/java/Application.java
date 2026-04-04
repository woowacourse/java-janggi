import controller.JanggiController;
import mapper.BoardOutputMapper;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        BoardOutputMapper boardOutputMapper = new BoardOutputMapper();

        JanggiController janggiController = new JanggiController(inputView, outputView, boardOutputMapper);
        janggiController.run();
    }

}
