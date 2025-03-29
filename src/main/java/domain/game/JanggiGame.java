package domain.game;

import domain.Coordinate;
import domain.board.Board;
import domain.board.BoardSettingUpStrategy;
import domain.piece.Country;
import domain.piece.Piece;
import infrastructure.BoardRepository;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    private Country currentTurn = Country.HAN;

    public void start() {
        Map<Coordinate, Piece> foundBoard = findBoard();
        Board board;
        if (foundBoard.isEmpty()) {
            board = settingUp();
            saveBoard(board);
            outputView.printNewGameMessage();
        } else {
            board = new Board(foundBoard);
            outputView.printPreviousGameMessage();
        }

        while (!isEndGame(board)) {
            takeTurn(board, this::movePiece);
            showScore(board);
            nextTurn();
        }
    }

    private Map<Coordinate, Piece> findBoard() {
        BoardRepository boardRepository = new BoardRepository();
        return boardRepository.findAll();
    }

    private void saveBoard(Board board) {
        BoardRepository boardRepository = new BoardRepository();
        boardRepository.save(board);
    }

    private Board settingUp() {
        BoardSettingUpStrategy hanSettingUpStrategy = retryUntilValid(() -> {
            String settingUp = inputView.readSettingUp(Country.HAN);
            return BoardSettingUpStrategy.selectStrategy(settingUp);
        });

        BoardSettingUpStrategy choSettingUpStrategy = retryUntilValid(() -> {
            String settingUp = inputView.readSettingUp(Country.CHO);
            return BoardSettingUpStrategy.selectStrategy(settingUp);
        });

        return new Board(hanSettingUpStrategy, choSettingUpStrategy);
    }

    private void movePiece(Board board) {
        outputView.printJanggiBoard(board);

        Coordinate from = retryUntilValid(() -> inputView.readMoveFrom(currentTurn.getCountryName()));
        board.validateIsMyPiece(from, currentTurn);
        Coordinate to = retryUntilValid(inputView::readMoveTo);

        board.movePiece(from, to);
    }

    private boolean isEndGame(Board board) {
        boolean isChoGungDead = board.isChoGungDead();
        boolean isHanGungDead = board.isHanGungDead();
        if (isChoGungDead || isHanGungDead) {
            outputView.printEndGame(isChoGungDead, isHanGungDead);
            return true;
        }
        return false;
    }

    private void showScore(Board board) {
        int hanScore = board.calculateHanScore();
        int choScore = board.calculateChoScore();

        outputView.printScore(hanScore, choScore);
    }

    private void nextTurn() {
        currentTurn = currentTurn.convertCountry();
    }

    private <T> void takeTurn(T value, Consumer<T> consumer) {
        while (true) {
            try {
                consumer.accept(value);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private <T> T retryUntilValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
