package janggi.controller;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.exception.ErrorException;
import janggi.piece.Camp;
import janggi.position.Position;
import janggi.view.Command;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class JanggiGame {

    private static final Camp FIRST_TURN = Camp.CHO;

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void runGame() {
        outputView.displayGameBanner();
        repeatUntilSuccess(inputView::askStartCommand);
        startGame();
    }

    private void startGame() {
        Board board = BoardGenerator.generate();
        GameStatus gameStatus = new GameStatus();
        Camp currentTurn = FIRST_TURN;

        while (gameStatus.isPlaying()) {
            outputView.displayBoard(board.getPlacedPieces());
            requestPlayGameUntilEnd(currentTurn, board, gameStatus);
            currentTurn = currentTurn.switchTurn();
        }
    }

    private void requestPlayGameUntilEnd(Camp baseCamp, Board board, GameStatus gameStatus) {
        repeatUntilSuccess(() -> {
            playGame(baseCamp, board, gameStatus);
        });
    }

    private void playGame(Camp baseCamp, Board board, GameStatus gameStatus) {
        Command command = repeatUntilSuccess(inputView::askPlayCommand);
        if (command == Command.END) {
            gameStatus.stopPlaying();
        }
        if (command == Command.MOVE) {
            processGame(inputView.readMovement(baseCamp), baseCamp, board);
        }
    }

    private void processGame(List<String> input, Camp baseCamp, Board board) {
        Position from = new Position(input.getFirst());
        Position to = new Position(input.getLast());
        validateSelectedPiece(board, from, baseCamp);
        board.move(from, to);
    }

    private void validateSelectedPiece(Board board, Position from, Camp baseCamp) {
        board.validateSelectedPiece(from, baseCamp);
    }

    private void repeatUntilSuccess(Runnable runner) {
        boolean success = false;
        while (!success) {
            success = tryRunWithErrorHandling(runner);
        }
    }

    private boolean tryRunWithErrorHandling(Runnable runner) {
        try {
            runner.run();
            return true;
        } catch (ErrorException e) {
            outputView.displayErrorMessage(e.getMessage());
            return false;
        }
    }

    private <T> T repeatUntilSuccess(Supplier<T> supplier) {
        Optional<T> result = Optional.empty();
        while (result.isEmpty()) {
            result = tryGetWithErrorHandling(supplier);
        }
        return result.get();
    }

    private <T> Optional<T> tryGetWithErrorHandling(Supplier<T> supplier) {
        try {
            return Optional.ofNullable(supplier.get());
        } catch (IllegalArgumentException e) {
            outputView.displayErrorMessage(e.getMessage());
            return Optional.empty();
        }
    }
}
