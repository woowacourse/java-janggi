import gameflow.JanggiGameFlow;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final JanggiGameFlow janggiGameFlow = new JanggiGameFlow(inputView, outputView);

        while (!janggiGameFlow.isEnd()) {
            try {
                janggiGameFlow.doTurn();
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }
}
