package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.piece.Piece;
import janggi.view.View;

public class Application {

    private static final Camp FIRST_TURN_CAMP = Camp.CHU;
    private static final int FROM_POINT_INDEX = 0;
    private static final int TO_POINT_INDEX = 1;
    private static final String ERROR_MESSAGE_FORMAT = "%n[ERROR] %s";

    public static void main(String[] args) {
        View view = new View();
        view.displayStartBanner();
        boolean startGame = view.readStartGame();
        if (startGame) {
            playGame(view);
        }
    }

    private static void playGame(View view) {
        Board board = BoardGenerator.generate();
        Camp currentTurnCamp = FIRST_TURN_CAMP;
        while (true) {
            view.displayBoard(board.getPlacedPieces());
            playTurnUntilSuccess(view, currentTurnCamp, board);
            currentTurnCamp = currentTurnCamp.reverse();
        }
    }

    private static void playTurnUntilSuccess(View view, Camp currentTurnCamp, Board board) {
        try {
            playTurn(view.readMove(currentTurnCamp), currentTurnCamp, board);
        } catch (IllegalArgumentException e) {
            System.out.printf(ERROR_MESSAGE_FORMAT, e.getMessage());
            playTurn(view.readMove(currentTurnCamp), currentTurnCamp, board);
        }
    }

    private static void playTurn(String[] input, Camp baseCamp, Board board) {
        Point from = new Point(input[FROM_POINT_INDEX]);
        Point to = new Point(input[TO_POINT_INDEX]);
        validateSelectedPiece(board, from, baseCamp);
        board.move(from, to);
    }

    private static void validateSelectedPiece(Board board, Point from, Camp baseCamp) {
        Piece piece = board.peek(from);
        piece.validateSelect(baseCamp);
    }
}
