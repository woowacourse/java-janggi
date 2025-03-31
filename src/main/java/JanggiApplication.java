import java.util.List;
import java.util.function.Supplier;

import board.Board;
import board.BoardInitializer;
import board.Position;
import dao.PieceConverter;
import dao.PieceDao;
import dao.PieceEntity;
import game.Turn;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final PieceDao pieceDao = new PieceDao();

    public static void main(String[] args) {
        Board board = createBoard();
        Turn turn = new Turn();

        outputView.printBoard(board.getPieces());
        outputView.printTeamScore(board.calculateTotalScore());
        playGame(board, turn);
    }

    private static Board createBoard() {
        List<PieceEntity> pieceEntities = pieceDao.findAll();
        if (pieceEntities.isEmpty()) {
            BoardInitializer boardInitializer = new BoardInitializer();
            Board board = new Board(boardInitializer.init());
            pieceDao.saveAll(board.getPieces().entrySet()
                    .stream()
                    .map(entry -> PieceConverter.toEntity(entry.getKey(), entry.getValue()))
                    .toList());
            return board;
        }
        return new Board(PieceConverter.toPieces(pieceEntities));
    }

    private static void playGame(final Board board, final Turn turn) {
        Position startPosition = retry(() -> readStartPosition(board, turn));
        retry(() -> movePosition(board, startPosition));
        outputView.printBoard(board.getPieces());
        outputView.printTeamScore(board.calculateTotalScore());
        turn.increaseRound();
        if (inputView.inputExitGame()) {
            return;
        }
        playGame(board, turn);
    }

    private static Position readStartPosition(final Board board, final Turn turn) {
        Position startPosition = inputView.readStartPosition();
        board.isValidTurn(startPosition, turn);
        return startPosition;
    }

    private static void movePosition(final Board board, final Position startPosition) {
        Position destinationPosition = inputView.readDestinationPosition();
        board.move(startPosition, destinationPosition);
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
