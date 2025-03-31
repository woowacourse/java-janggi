import board.JdbcGameBoard;
import dao.PieceDao;
import dao.PlayerDao;
import dao.TurnDao;
import game.JanggiGame;

public class Application {
    public static void main(String[] args) {
        JdbcGameBoard jdbcGameBoard = new JdbcGameBoard(new PieceDao(), new PlayerDao(), new TurnDao());
        JanggiGame janggiGame = new JanggiGame(jdbcGameBoard);
        janggiGame.initialize();

        janggiGame.run();
    }
}
