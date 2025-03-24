package janggi.controller;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.exception.ErrorException;
import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameController {

    private static final Camp FIRST_TURN_CAMP = Camp.CHO;

    private final InputView inputView;
    private final OutputView outputView;

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void runGame() {
        outputView.displayStartBanner();
        if (startGame()) {
            playGame();
        }
    }

    private boolean startGame() {
        AtomicBoolean startGame = new AtomicBoolean(false);
        repeatUntilSuccess(() -> {
            startGame.set(inputView.readStartGame());
        });
        return startGame.get();
    }

    private void playGame() {
        Board board = BoardGenerator.generate();
        Camp currentTurnCamp = FIRST_TURN_CAMP;
        while (true) {
            outputView.displayBoard(board.getPlacedPieces());
            requestPlayGameUntilSuccess(currentTurnCamp, board);
            currentTurnCamp = currentTurnCamp.opposite();
        }
    }

    private void requestPlayGameUntilSuccess(Camp currentTurnCamp, Board board) {
        repeatUntilSuccess(() -> {
            playTurn(inputView.readMovement(currentTurnCamp), currentTurnCamp, board);
        });
    }

    private void playTurn(List<String> input, Camp baseCamp, Board board) {
        Position from = new Position(input.getFirst());
        Position to = new Position(input.getLast());
        validateSelectedPiece(board, from, baseCamp);
        board.move(from, to);
    }

    private void validateSelectedPiece(Board board, Position from, Camp baseCamp) {
        Piece piece = board.peek(from);
        piece.validateSelect(baseCamp);
    }

    private void repeatUntilSuccess(Runnable runner) {
        boolean success = false;
        while (!success) {
            success = run(runner);
        }
    }

    private boolean run(Runnable runner) {
        try {
            runner.run();
            return true;
        } catch (ErrorException e) {
            outputView.displayErrorMessage(e.getMessage());
            return false;
        }
    }
}
