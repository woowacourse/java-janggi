import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import board.Board;
import board.BoardInitializer;
import board.Position;
import dao.PieceConverter;
import dao.PieceDao;
import dao.PieceEntity;
import dao.TurnConverter;
import dao.TurnDao;
import game.Turn;
import piece.Piece;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final PieceDao pieceDao = new PieceDao();
    private static final TurnDao turnDao = new TurnDao();

    public static void main(String[] args) {
        Board board = createBoard();
        Turn turn = createTurn();

        outputView.printBoard(board.getPieces());
        outputView.printTeamScore(board.calculateTotalScore());
        playGame(board, turn);
    }

    private static Board createBoard() {
        List<PieceEntity> pieceEntities = pieceDao.findAll();
        if (pieceEntities.isEmpty()) {
            BoardInitializer boardInitializer = new BoardInitializer();
            Board board = new Board(boardInitializer.init());
            List<PieceEntity> entitiesToSave = createPieceEntities(board.getPieces());
            pieceDao.saveAll(entitiesToSave);
            return board;
        }
        return new Board(PieceConverter.toPieces(pieceEntities));
    }

    private static List<PieceEntity> createPieceEntities(final Map<Position, Piece> pieces) {
        return pieces.entrySet()
                .stream()
                .map(entry -> PieceConverter.toEntity(entry.getKey(), entry.getValue()))
                .toList();
    }

    private static Turn createTurn() {
        if (turnDao.exists()) {
            return TurnConverter.toTurn(turnDao.find());
        }
        Turn turn = new Turn();
        turnDao.save(TurnConverter.toEntity(turn));
        return turn;
    }

    private static void playGame(final Board board, final Turn turn) {
        Position startPosition = retry(() -> readStartPosition(board, turn));
        retry(() -> movePosition(board, startPosition));
        outputView.printBoard(board.getPieces());
        outputView.printTeamScore(board.calculateTotalScore());
        turn.increaseRound();
        turnDao.update(TurnConverter.toEntity(turn));
        if (board.isFinish()) {
            outputView.printWinner(board.findWinnerTeam());
            pieceDao.removeAll();
            turnDao.removeAll();
            return;
        }
        playGame(board, turn);
    }

    private static Position readStartPosition(final Board board, final Turn turn) {
        Position startPosition = inputView.readStartPosition(turn);
        board.isValidTurn(startPosition, turn);
        return startPosition;
    }

    private static void movePosition(final Board board, final Position startPosition) {
        Position destinationPosition = inputView.readDestinationPosition();
        board.move(startPosition, destinationPosition);
        pieceDao.removePieceByPosition(destinationPosition);
        pieceDao.updatePieceByOriginPosition(startPosition, destinationPosition);
    }

    private static <T> T retry(final Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
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
