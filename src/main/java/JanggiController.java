import domain.Board;
import domain.BoardFactory;
import domain.vo.Position;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = BoardFactory.setUp();
        outputView.printBoard(board.getBoard());

        movePosition(board);

        while (inputView.readRetryCommand()) {
            movePosition(board);
        }
    }

    private void movePosition(Board board) {
        try {
            Position position = inputView.readPosition();
            Position targetPosition = inputView.readTargetPosition();

            board.move(position, targetPosition);
            outputView.printBoard(board.getBoard());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            movePosition(board);
        }
    }
}
