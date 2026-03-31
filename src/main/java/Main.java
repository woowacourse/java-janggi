import controller.JanggiGameController;
import view.InputView;
import view.ResultView;

public class Main {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        ResultView resultView = new ResultView();
        JanggiGameController janggiGameController = new JanggiGameController(inputView, resultView);

        janggiGameController.play();
    }
}
