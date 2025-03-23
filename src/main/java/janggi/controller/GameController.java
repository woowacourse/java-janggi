package janggi.controller;

import janggi.Camp;
import janggi.Point;
import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.exception.ErrorException;
import janggi.piece.Piece;
import janggi.view.View;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameController {

    private static final Camp FIRST_TURN_CAMP = Camp.CHU;
    private static final int FROM_POINT_INDEX = 0;
    private static final int TO_POINT_INDEX = 1;

    private final View view;

    public GameController(View view) {
        this.view = view;
    }

    public void runGame() {
        view.displayStartBanner();
        if (startGame()) {
            playGame();
        }
    }

    private boolean startGame() {
        AtomicBoolean startGame = new AtomicBoolean(false);
        repeatUntilSuccess(() -> {
            startGame.set(view.readStartGame());
        });
        return startGame.get();
    }

    private void playGame() {
        Board board = BoardGenerator.generate();
        Camp currentTurnCamp = FIRST_TURN_CAMP;
        while (true) {
            view.displayBoard(board.getPlacedPieces());
            requestPlayGameUntilSuccess(currentTurnCamp, board);
            currentTurnCamp = currentTurnCamp.opposite();
        }
    }

    private void requestPlayGameUntilSuccess(Camp currentTurnCamp, Board board) {
        repeatUntilSuccess(() -> {
            playTurn(view.readMove(currentTurnCamp), currentTurnCamp, board);
        });
    }

    private void playTurn(String[] input, Camp baseCamp, Board board) {
        Point from = new Point(input[FROM_POINT_INDEX]);
        Point to = new Point(input[TO_POINT_INDEX]);
        validateSelectedPiece(board, from, baseCamp);
        board.move(from, to);
    }

    private void validateSelectedPiece(Board board, Point from, Camp baseCamp) {
        Piece piece = board.peek(from);
        piece.validateSelect(baseCamp);
    }

    public void repeatUntilSuccess(Runnable runner) {
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
            view.displayErrorMessage(e.getMessage());
            return false;
        }
    }
}
