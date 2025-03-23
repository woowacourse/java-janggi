package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.board.point.Point;
import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.view.PointParser;
import janggi.view.View;

public final class Application {

    private static final Camp FIRST_TURN_CAMP = Camp.CHU;

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
            playTurn(view, currentTurnCamp, board);
        } catch (IllegalArgumentException e) {
            view.displayErrorMessage(e.getMessage());
            playTurn(view, currentTurnCamp, board);
        }
    }

    private static void playTurn(View view, Camp baseCamp, Board board) {
        view.displayCurrentTurnCamp(baseCamp);
        Point fromPoint = PointParser.parse(view.readMoveFromPoint());
        Point toPoint = PointParser.parse(view.readMoveToPoint());
        validateSelectedPiece(board, fromPoint, baseCamp);
        board.movePiece(fromPoint, toPoint);
    }

    private static void validateSelectedPiece(Board board, Point from, Camp baseCamp) {
        Piece piece = board.getPiece(from);
        piece.validateSelect(baseCamp);
    }
}
