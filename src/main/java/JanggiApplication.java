import janggi.dao.BoardDao;
import janggi.dao.DatabaseManager;
import janggi.dao.PieceDao;
import janggi.dao.connection.MysqlConnection;
import janggi.game.Game;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final MysqlConnection mysqlConnection = new MysqlConnection();
        final BoardDao boardDao = new BoardDao(mysqlConnection);
        final PieceDao pieceDao = new PieceDao(mysqlConnection);
        final DatabaseManager databaseManager = new DatabaseManager(boardDao, pieceDao);

        final Game game = new Game(inputView, outputView, databaseManager);
        game.play();
    }
}
