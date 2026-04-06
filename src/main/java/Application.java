import controller.GameController;
import domain.board.Board;
import domain.board.BoardInitializer;
import view.InputView;

public class Application {
    public static void main(String[] args) {
        Board board = new Board(BoardInitializer.init(InputView.readBoardSetting()));
        GameController gameController = new GameController(board);

        while (!board.isGameOver()) {
            gameController.printBoard();
            gameController.move();
        }

        gameController.printBoard();
        gameController.printWinner();
    }
}
