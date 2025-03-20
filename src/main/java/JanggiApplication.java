import java.util.function.Supplier;

import domain.Board;
import domain.BoardInitializer;
import domain.Position;
import domain.Turn;
import domain.piece.Piece;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        BoardInitializer boardInitializer = new BoardInitializer();
        Board board = boardInitializer.init();
        Turn turn = new Turn();
        outputView.printBoard(board);
        playGame(board, turn);
    }

    private static void playGame(final Board board, final Turn turn) {
        Piece piece = retry(() -> inputMovePosition(board, turn));
        retry(() -> movePosition(piece));
        outputView.printBoard(board);
        turn.increaseRound();
        if (inputView.inputExitGame()) {
            return;
        }
        playGame(board, turn);
    }

    private static Piece inputMovePosition(final Board board, final Turn turn) {
        Position movePosition = inputView.inputMovePiecePosition();
        return board.findPiece(movePosition, turn.getCurrnetTeam());
    }

    private static void movePosition(final Piece piece) {
        Position targetPosition = inputView.inputMoveTargetPosition();
        piece.move(targetPosition);
    }

    private static <T> T retry(final Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void retry(final Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

}
