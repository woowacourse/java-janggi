import controller.JanggiGame;
import view.InputReader;
import view.OutputWriter;

public class Application {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        OutputWriter outputWriter = new OutputWriter();

        JanggiGame janggiGame = new JanggiGame(inputReader, outputWriter);
        janggiGame.run();
    }

}
