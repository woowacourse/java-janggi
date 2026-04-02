import controller.GameController;
import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.BoardSetting;

public class Application {
    public static void main(String[] args) {
        Board board = new Board(BoardInitializer.init(BoardSetting.LEFT_ELEPHANT_SET_UP));
        GameController gameController = new GameController();

        while (!board.isGameOver()) {
            gameController.printBoard(board);
            gameController.move(board);
        }

        gameController.printBoard(board);
        gameController.printWinner(board);
    }
}
