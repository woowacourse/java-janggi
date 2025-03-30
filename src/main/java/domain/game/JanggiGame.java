package domain.game;

import domain.Coordinate;
import domain.board.Board;
import domain.board.BoardSettingUpStrategy;
import domain.piece.Country;
import domain.piece.Piece;
import infrastructure.BoardRepository;
import infrastructure.TurnRepository;
import java.util.Map;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardRepository boardRepository;
    private final TurnRepository turnRepository;
    private Turn turn;

    public JanggiGame(
            InputView inputView, OutputView outputView,
            BoardRepository boardRepository, TurnRepository turnRepository
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardRepository = boardRepository;
        this.turnRepository = turnRepository;
        this.turn = new Turn(Country.CHO);
    }

    public void play() {
        Board board = start();

        while (!isEndGame(board)) {
            turn.take(board, this::movePiece);
            showScore(board);
            nextTurn();
        }
    }

    private Board start() {
        Map<Coordinate, Piece> foundBoard = boardRepository.findAll();
        Board board;
        if (foundBoard.isEmpty()) {
            board = settingUp();
            boardRepository.save(board);
            turnRepository.save(turn);
            outputView.printNewGameMessage();
            return board;
        }
        board = new Board(foundBoard);
        turn = turnRepository.findTurn();
        outputView.printPreviousGameMessage();
        return board;
    }

    private Board settingUp() {
        BoardSettingUpStrategy hanSettingUpStrategy = retryUntilValid(() ->
                BoardSettingUpStrategy.selectStrategy(inputView.readSettingUp(Country.HAN))
        );

        BoardSettingUpStrategy choSettingUpStrategy = retryUntilValid(() ->
                BoardSettingUpStrategy.selectStrategy(inputView.readSettingUp(Country.CHO)));

        return new Board(hanSettingUpStrategy, choSettingUpStrategy);
    }

    private void movePiece(Board board) {
        outputView.printJanggiBoard(board);

        Coordinate from = retryUntilValid(() -> inputView.readMoveFrom(turn.getCurrentName()));
        board.validateIsMyPiece(from, turn.getCountry());
        Coordinate to = retryUntilValid(inputView::readMoveTo);

        board.movePiece(from, to);

        updateBoard(board);
    }

    private void updateBoard(Board board) {
        boardRepository.deleteAll();
        boardRepository.save(board);
    }

    private boolean isEndGame(Board board) {
        boolean isChoGungDead = board.isChoGungDead();
        boolean isHanGungDead = board.isHanGungDead();
        if (isChoGungDead || isHanGungDead) {
            boardRepository.deleteAll();
            turnRepository.delete();
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
        turn.next();
        turnRepository.updateTurn(turn);
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
