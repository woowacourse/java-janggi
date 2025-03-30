package application;

import application.persistence.BoardRepository;
import application.persistence.TurnRepository;
import domain.Coordinate;
import domain.board.Board;
import domain.board.setting.ChoSettingUpStrategy;
import domain.board.setting.HanSettingUpStrategy;
import domain.game.Turn;
import domain.piece.Country;
import java.util.function.Consumer;
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
            takeTurn(board, this::movePiece);
            showScore(board);
            nextTurn();
        }
    }

    private Board start() {
        Board stored = boardRepository.findAll();
        if (stored.isEmpty()) {
            return newGame();
        }
        return previousGame(stored);
    }

    private Board newGame() {
        Board board = settingUp();
        boardRepository.saveAll(board);
        turnRepository.save(turn);
        outputView.printNewGameMessage();
        return board;
    }

    private Board previousGame(Board savedBoard) {
        turn = turnRepository.findTurn();
        outputView.printPreviousGameMessage();
        return savedBoard;
    }

    public void takeTurn(Board board, Consumer<Board> consumer) {
        while (true) {
            try {
                consumer.accept(board);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Board settingUp() {
        HanSettingUpStrategy hanSettingUpStrategy = retryUntilValid(() ->
                HanSettingUpStrategy.selectStrategy(inputView.readSettingUp(Country.HAN)));

        ChoSettingUpStrategy choSettingUpStrategy = retryUntilValid(() ->
                ChoSettingUpStrategy.selectStrategy(inputView.readSettingUp(Country.CHO)));

        return new Board(choSettingUpStrategy, hanSettingUpStrategy);
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
        boardRepository.saveAll(board);
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
