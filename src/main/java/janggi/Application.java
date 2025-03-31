package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.board.Point;
import janggi.camp.Camp;
import janggi.dao.PieceDao;
import janggi.piece.Piece;
import janggi.view.View;

public class Application {

    private static final Camp FIRST_TURN_CAMP = Camp.CHU;

    public static void main(String[] args) {
        View view = new View();
        PieceDao pieceDao = new PieceDao();
        view.displayStartBanner();
        boolean startGame = view.readStartGame();
        if (startGame) {
            playGame(view, pieceDao);
        }
    }

    private static void playGame(View view, PieceDao pieceDao) {
        Board board = BoardGenerator.generate(pieceDao);
        Camp currentTurnCamp = FIRST_TURN_CAMP;
        while (!board.isGameOver()) {
            view.displayBoard(board.getPieceDao());
            currentTurnCamp = tryPlayTurn(view, currentTurnCamp, board);
        }
        handleGameEnd(view, board);
        view.displayScore(Camp.CHU, board.calculateChuScore());
        view.displayScore(Camp.HAN, board.calculateHanScore());
    }

    private static Camp tryPlayTurn(View view, Camp currentTurnCamp, Board board) {
        try {
            String fromPointInput = view.readFromPoint(currentTurnCamp);
            String toPointInput = view.readToPoint();
            executeTurn(fromPointInput, toPointInput, currentTurnCamp, board);
            return currentTurnCamp.reverse();
        } catch (IllegalArgumentException e) {
            view.displayErrorMessage(e.getMessage());
            return currentTurnCamp;
        }
    }

    private static void executeTurn(String fromPointInput, String toPointInput, Camp baseCamp, Board board) {
        Point from = new Point(fromPointInput);
        Point to = new Point(toPointInput);
        validateSelectedPiece(board, from, baseCamp);
        board.move(from, to);
    }

    private static void validateSelectedPiece(Board board, Point from, Camp baseCamp) {
        Piece piece = board.peek(from);
        piece.validateSelect(baseCamp);
    }

    private static void handleGameEnd(View view, Board board) {
        Camp winningCamp = board.findWinningCamp();
        view.displayBoard(board.getPieceDao());
        view.displayEndingMessage(winningCamp);
        board.resetBoard();
    }
}
