package janggi;

import janggi.controller.BoardInitializeController;
import janggi.controller.GameController;
import janggi.dao.PieceDao;
import janggi.domain.board.Board;
import janggi.service.GameService;
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

        Board board = initializeBoard(repository);

        GameService gameService = new GameService(board, repository);
        GameController controller = new GameController(inputView, outputView, gameService);
        controller.play();
    }

    private static Board initializeBoard(final Repository repository) {
        BoardInitiliazeView boardInitiliazeView = new BoardInitiliazeView(new Scanner(System.in));

        BoardInitializeController boardInitializeController = new BoardInitializeController(boardInitiliazeView, repository);
        return boardInitializeController.initializeBoard();
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
