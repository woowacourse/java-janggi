package janggi.controller;

import janggi.board.Board;
import janggi.board.InitialBoardGenerator;
import janggi.exception.ErrorException;
import janggi.piece.Camp;
import janggi.position.Movement;
import janggi.position.Position;
import janggi.view.Command;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Map;
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
        GameMode gameMode = new GameMode();
        Camp currentTurn = FIRST_TURN;
        InitialBoardGenerator initialBoardGenerator = new InitialBoardGenerator();
        Board board = initialBoardGenerator.generate(currentTurn);

        while (gameMode.isPlaying()) {
            showBoard(board);
            requestPlayGameUntilEnd(currentTurn, board, gameMode);
            currentTurn = currentTurn.switchTurn();
        }
    }

    private void requestPlayGameUntilEnd(Camp baseCamp, Board board, GameMode gameMode) {
        repeatUntilSuccess(() -> {
            playGame(baseCamp, board, gameMode);
        });
    }

    private void playGame(Camp baseCamp, Board board, GameMode gameMode) {
        Command command = repeatUntilSuccess(inputView::askPlayCommand);
        if (command == Command.END) {
            gameMode.stopPlaying();
        }
        if (command == Command.MOVE) {
            processAndShowGame(inputView.readMovement(baseCamp), board, gameMode);
        }
    }

    private void processAndShowGame(List<List<Integer>> input, Board board, GameMode gameMode) {
        processBoard(input, board);
        processAndShowResult(board, gameMode);
    }

    private void processAndShowResult(Board board, GameMode gameMode) {
        showBoard(board);
        showScore(board);
        Camp winner = board.determineWinner();
        if (winner != Camp.NONE) {
            gameMode.stopPlaying();
            showWinner(winner);
        }
    }

    private void processBoard(List<List<Integer>> input, Board board) {
        Position origin = parsePositionOf(input.getFirst());
        Position target = parsePositionOf(input.getLast());
        Movement movement = new Movement(origin, target);
        board.move(movement);
    }


    private void showBoard(Board board) {
        outputView.displayBoard(board.getCells());
    }

    private void showScore(Board board) {
        Map<Camp, Double> scores = board.determineScores(FIRST_TURN);
        outputView.displayScores(scores);
    }

    private void showWinner(Camp winner) {
        outputView.displayWinner(winner);
    }

    private Position parsePositionOf(List<Integer> input) {
        return Position.of(input.getFirst(), input.getLast());
    }

    // 재입력 받는 로직
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
