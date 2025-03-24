import domain.Board;
import domain.BoardInitializer;
import domain.Position;
import domain.Turn;
import domain.piece.Piece;
import java.util.List;
import view.InputView;
import view.OutputView;
import view.Parser;

public class JanggiApplication {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        BoardInitializer boardInitializer = new BoardInitializer();
        Board board = boardInitializer.init();
        Turn turn = new Turn();
        outputView.printBoard(board);
        retry(() -> playGame(board, turn));
    }

    private static void playGame(final Board board, final Turn turn) {
        outputView.printBoard(board);
        String command = inputView.inputMovePositions();
        if (command.equals("Q")) {
            return;
        }
        List<Position> positions = Parser.parsePositions(command);
        Position startPosition = positions.get(0);
        Position endPosition = positions.get(1);
        Piece piece = board.findPiece(startPosition);
        piece.move(endPosition);
        turn.increaseRound();
        outputView.printBoard(board);
    }

    private static void retry(final Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            } catch (Exception e) {
                outputView.printError("예상치 못한 예외가 발생했습니다.");
            }
        }
    }

}
