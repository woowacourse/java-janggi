import controller.JanggiController;
import janggiGame.JanggiGame;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiGame janggiGame = new JanggiGame();
        JanggiController controller = new JanggiController(janggiGame, inputView, outputView);

        controller.arrangePieces();

        while (!janggiGame.isFinished()) {
            try {
                outputView.printBoard(janggiGame.getPieces());

                int option = inputView.getTurnOption(janggiGame.getCurrentDynasty());
                controller.selectOption(option);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        controller.printGameResult();
    }
}
