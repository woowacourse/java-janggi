import dao.BoardDao;
import dao.PieceDao;
import game.BoardInitializer;
import game.JanggiGame;
import location.PathManager;
import location.PathManagerImpl;

public class Application {

    public static void main(String[] args) {
        BoardDao boardDao = new BoardDao();
        PieceDao pieceDao = new PieceDao();
        PathManager pathManager = new PathManagerImpl();
        BoardInitializer boardInitializer = new BoardInitializer(pathManager, pieceDao);
        JanggiGame janggiGame = new JanggiGame(boardDao, pieceDao, boardInitializer);

        janggiGame.showBoard();
        janggiGame.run();
    }
}
