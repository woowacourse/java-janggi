import domain.BoardFactory;
import domain.JanggiGame;

public class Application {

    public static void main(final String[] args) {
        final JanggiGame janggiGame = new JanggiGame(BoardFactory.create());
        janggiGame.start();
    }
}
