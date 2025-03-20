import domain.Board;
import domain.JanggiGame;

public class Application {

    public static void main(final String[] args) {
        final JanggiGame janggiGame = new JanggiGame(Board.initialize());
        janggiGame.start();
    }
}
