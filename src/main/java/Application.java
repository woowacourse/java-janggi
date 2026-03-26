import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager(
                new InputView(new Scanner(System.in)),
                new OutputView()
        );
        gameManager.start();
    }
}
