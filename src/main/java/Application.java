import board.MemoryGameBoard;
import game.JanggiGame;

public class Application {
    public static void main(String[] args) {
        JanggiGame janggiGame = new JanggiGame(new MemoryGameBoard());
        janggiGame.showInitialBoard();

        janggiGame.run();
    }
}
