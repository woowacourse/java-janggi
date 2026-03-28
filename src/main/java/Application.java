import view.OutputView;

public class Application {

    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        JanggiController janggiController = new JanggiController(outputView);
        janggiController.run();
    }
}
