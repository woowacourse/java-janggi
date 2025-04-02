import dao.BoardDao;
import dao.PieceDao;
import dao.PlayerDao;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView(new Scanner(System.in));
        PlayerDao playerDao = new PlayerDao();
        PieceDao pieceDao = new PieceDao();
        BoardDao boardDao = new BoardDao();

        KoreaChess koreaChess = new KoreaChess(
                outputView, inputView,
                pieceDao, playerDao, boardDao
        );

        koreaChess.run();
    }
}
