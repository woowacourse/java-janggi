import domain.Turn;
import java.util.function.Supplier;

import domain.Board;
import domain.BoardInitializer;
import domain.Position;
import domain.piece.Piece;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        BoardInitializer boardInitializer = new BoardInitializer();
        Board board = boardInitializer.init();
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();
        Turn turn = new Turn();
        outputView.printBoard(board);
        playGame(inputView, board, outputView, turn);
    }
    
    private static void playGame(InputView inputView, Board board, OutputView outputView, Turn turn) {
            Piece piece = retry(() -> inputMovePosition(inputView, board, turn));
            retry(() -> movePosition(inputView, piece));
            outputView.printBoard(board);
            turn.increaseRound();
            if(inputView.inputExitGame()) {
                return;
            }
            playGame(inputView, board, outputView, turn);
    }

    private static Piece inputMovePosition(final InputView inputView, final Board board, final Turn turn) {
        Position movePosition = inputView.inputMovePiecePosition();
        return board.findPiece(movePosition, turn.getCurrnetTeam());
    }

    private static void movePosition(final InputView inputView, final Piece piece) {
        Position targetPosition = inputView.inputMoveTargetPosition();
        piece.move(targetPosition);
    }

    private static <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void retry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
