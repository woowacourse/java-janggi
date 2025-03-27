import store.Board;
import game.JanggiGame;

public class Application {

    public static void main(String[] args) {
        JanggiGame janggiGame = new JanggiGame(new Board());
        janggiGame.showInitialBoard();

        janggiGame.run();
    }
}
