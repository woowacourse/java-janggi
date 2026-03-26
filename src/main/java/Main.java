import view.InputView;

public class Main {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        Controller controller = new Controller(inputView);

        controller.initializeGame();
    }
}
