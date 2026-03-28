import ui.Controller;
import ui.view.InputView;
import ui.view.ResultView;

public class Main {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        ResultView resultView = new ResultView();
        Controller controller = new Controller(inputView, resultView);

        controller.play();
    }
}
