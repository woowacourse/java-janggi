import dao.BoardDao;
import dao.PieceDao;
import game.JanggiGame;

public class Application {

    public static void main(String[] args) {
        JanggiGame janggiGame = new JanggiGame(new BoardDao(), new PieceDao());
        janggiGame.showInitialBoard();

        janggiGame.run();
    }
}
