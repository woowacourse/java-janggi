import controller.JanggiController;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        JanggiController controller = new JanggiController(outputView);
        controller.start();
    }
}
