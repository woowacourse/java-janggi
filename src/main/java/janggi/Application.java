package janggi;

import janggi.dao.PieceDao;
import janggi.domain.board.Board;
import janggi.view.ConfigurationView;
import java.util.Scanner;
import janggi.repository.DockerRepository;
import janggi.repository.Repository;
import janggi.repository.MemoryRepository;
import janggi.view.BoardInitiliazeView;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Repository repository = decideRepository();

        BoardInitializer boardInitializer = new BoardInitializer(new BoardInitiliazeView(new Scanner(System.in)), repository);
        Board board = boardInitializer.initializeBoard();

        GameService gameService = new GameService(board, repository);
        GameController controller = new GameController(inputView, outputView, gameService);
        controller.play();
    }

    private static Repository decideRepository() {
        final var pieceDao = new PieceDao();
        if (pieceDao.getConnection() == null) {
            ConfigurationView.printConnectionFailed();
            return new MemoryRepository();
        }

        return new DockerRepository(pieceDao);
    }
}
