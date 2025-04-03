import dao.BoardDao;
import dao.DatabaseConnectionManager;
import dao.PieceDao;
import dao.PlayerDao;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView(new Scanner(System.in));
        DatabaseConnectionManager databaseManager = new DatabaseConnectionManager();
        PlayerDao playerDao = new PlayerDao(databaseManager);
        PieceDao pieceDao = new PieceDao(databaseManager);
        BoardDao boardDao = new BoardDao(databaseManager);

        KoreaChess koreaChess = new KoreaChess(
                outputView, inputView,
                pieceDao, playerDao, boardDao
        );

        koreaChess.run();
    }
}
