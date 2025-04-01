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
        while (!board.isGameOver()) {
            view.displayBoard(board.getPieceDao());
            if (view.readGameCommand().equals("end")) {
                break;
            }
            tryPlayTurn(view, board, boardDao);
        }
        if (board.isGameOver()) {
            handleGameEnd(view, board, boardDao);
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

    private static void tryPlayTurn(View view, Board board, BoardDao boardDao) {
        Camp currentTurnCamp = boardDao.findLatestTurn();
        try {
            String fromPointInput = view.readFromPoint(currentTurnCamp);
            String toPointInput = view.readToPoint();
            executeTurn(fromPointInput, toPointInput, currentTurnCamp, board);
            boardDao.updateTurn(currentTurnCamp.reverse());
        } catch (IllegalArgumentException e) {
            view.displayErrorMessage(e.getMessage());
            boardDao.updateTurn(currentTurnCamp);
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

    private static void handleGameEnd(View view, Board board, BoardDao boardDao) {
        Camp winningCamp = board.findWinningCamp();
        view.displayBoard(board.getPieceDao());
        view.displayEndingMessage(winningCamp);
        view.displayScore(Camp.CHU, board.calculateChuScore());
        view.displayScore(Camp.HAN, board.calculateHanScore());
        boardDao.endBoard();
        board.resetBoard();
    }
}
