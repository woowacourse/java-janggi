package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.board.Point;
import janggi.camp.Camp;
import janggi.dao.BoardDao;
import janggi.piece.Piece;
import janggi.view.View;

public class Application {

    private static final Camp FIRST_TURN_CAMP = Camp.CHU;

    public static void main(String[] args) {
        View view = new View();
        BoardDao boardDao = new BoardDao();
        view.displayStartBanner();
        boolean startGame = view.readStartGame();
        if (startGame) {
            playGame(view, boardDao);
        }
    }

    private static void playGame(View view, BoardDao boardDao) {
        Board board = initializeIfNewGame(boardDao);
        Camp currentTurnCamp = boardDao.findLatestTurn();
        while (!board.isGameOver()) {
            view.displayBoard(board.getPieceDao());
            if (view.readGameCommand(currentTurnCamp).equals("end")) {
                break;
            }
            currentTurnCamp = handleTurn(view, boardDao, board, currentTurnCamp);
        }
        if (board.isGameOver()) {
            endGame(view, board, boardDao);
        }
    }

    private static Board initializeIfNewGame(BoardDao boardDao) {
        if (boardDao.isNewGame()) {
            Board board = BoardGenerator.generate();
            boardDao.initializeBoard(FIRST_TURN_CAMP);
            return board;
        }
        return new Board();
    }

    private static Camp handleTurn(View view, BoardDao boardDao, Board board, Camp currentTurnCamp) {
        boolean turnPlayed = tryPlayTurn(view, board, currentTurnCamp);
        if (!turnPlayed) {
            return currentTurnCamp;
        }
        Camp nextTurn = currentTurnCamp.reverse();
        boardDao.updateTurn(nextTurn);
        return nextTurn;
    }

    private static boolean tryPlayTurn(View view, Board board, Camp currentTurnCamp) {
        try {
            String fromPointInput = view.readFromPoint();
            String toPointInput = view.readToPoint();
            executeTurn(fromPointInput, toPointInput, currentTurnCamp, board);
            return true;
        } catch (IllegalArgumentException e) {
            view.displayErrorMessage(e.getMessage());
            return false;
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

    private static void endGame(View view, Board board, BoardDao boardDao) {
        displayEndingResult(view, board);
        boardDao.endBoard();
        board.resetBoard();
    }

    private static void displayEndingResult(View view, Board board) {
        view.displayBoard(board.getPieceDao());
        Camp winningCamp = board.findWinningCamp();
        view.displayEndingMessage(winningCamp);
        view.displayScore(Camp.CHU, board.calculateChuScore());
        view.displayScore(Camp.HAN, board.calculateHanScore());
    }
}
