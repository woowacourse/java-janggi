import domain.GameManager;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager(
                new InputView(new Scanner(System.in)),
                new OutputView()
        );
        gameManager.play();
    }
}
