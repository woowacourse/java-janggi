package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.board.Point;
import janggi.camp.Camp;
import janggi.piece.Piece;
import janggi.view.View;
import java.util.List;

public class Application {

    private static final String ERROR_MESSAGE_FORMAT = "%n[ERROR] %s";
    private static final Camp FIRST_TURN_CAMP = Camp.CHU;
    private static final int FROM_POINT_INDEX = 0;
    private static final int TO_POINT_INDEX = 1;
    public static final int X_COORDINATE = 0;
    public static final int Y_COORDINATE = 1;

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
        while (true) {
            try {
                List<List<Integer>> moveInput = view.readMove(currentTurnCamp);
                playTurn(moveInput, currentTurnCamp, board);
                return;
            } catch (IllegalArgumentException e) {
                System.out.printf(ERROR_MESSAGE_FORMAT, e.getMessage());
            }
        }
    }

    private static void playTurn(List<List<Integer>> moveInputs, Camp baseCamp, Board board) {
        Point from = createPointFromInput(moveInputs, FROM_POINT_INDEX);
        Point to = createPointFromInput(moveInputs, TO_POINT_INDEX);
        validateSelectedPiece(board, from, baseCamp);
        board.move(from, to);
    }

    private static Point createPointFromInput(List<List<Integer>> moveInputs, int index) {
        List<Integer> coordinates = moveInputs.get(index);
        return new Point(coordinates.get(X_COORDINATE), coordinates.get(Y_COORDINATE));
    }

    private static void validateSelectedPiece(Board board, Point from, Camp baseCamp) {
        Piece piece = board.peek(from);
        piece.validateSelect(baseCamp);
    }
}
