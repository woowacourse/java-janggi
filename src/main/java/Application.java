import board.GameBoard;
import dao.PieceDao;
import dao.TurnDao;
import game.JanggiGame;

public class Application {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(new PieceDao(), new TurnDao());
        JanggiGame janggiGame = new JanggiGame(gameBoard);
        janggiGame.initialize();

        janggiGame.run();
    }
}
