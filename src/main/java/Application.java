import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView(new Scanner(System.in));

        try {
            KoreaChess koreaChess = new KoreaChess(
                    outputView,
                    inputView
            );

            koreaChess.run();
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }
}
