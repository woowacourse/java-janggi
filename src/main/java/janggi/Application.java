package janggi;

import janggi.dao.GameDao;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import janggi.domain.board.Point;
import janggi.domain.camp.Camp;
import janggi.domain.piece.Piece;
import janggi.infra.DatabaseConfig;
import janggi.infra.DatabaseConnector;
import janggi.view.View;

public class Application {

    private static final Camp FIRST_TURN_CAMP = Camp.CHU;

    public static void main(String[] args) {
        View view = new View();
        DatabaseConfig DBConfig = new DatabaseConfig("localhost:13306", "janggi",
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "root");
        DatabaseConnector DBConnector = new DatabaseConnector(DBConfig);
        GameDao gameDao = new GameDao(DBConnector);
        view.displayStartBanner();
        boolean startGame = view.readStartGame();
        if (startGame) {
            playGame(view, gameDao);
        }
    }

    private static void playGame(View view, GameDao gameDao) {
        Board board = initializeIfNewGame(gameDao);
        Camp currentTurnCamp = gameDao.findLatestTurn();
        while (!board.isGameOver()) {
            view.displayBoard(board.getPieceDao());
            if (view.readGameCommand(currentTurnCamp).equals("end")) {
                break;
            }
            currentTurnCamp = handleTurn(view, gameDao, board, currentTurnCamp);
        }
        if (board.isGameOver()) {
            endGame(view, board, gameDao);
        }
    }

    private static Board initializeIfNewGame(GameDao gameDao) {
        if (gameDao.isNewGame()) {
            Board board = BoardGenerator.generate();
            gameDao.initializeGame(FIRST_TURN_CAMP);
            return board;
        }
        return new Board();
    }

    private static Camp handleTurn(View view, GameDao gameDao, Board board, Camp currentTurnCamp) {
        boolean turnPlayed = tryPlayTurn(view, board, currentTurnCamp);
        if (!turnPlayed) {
            return currentTurnCamp;
        }
        Camp nextTurn = currentTurnCamp.reverse();
        gameDao.updateTurn(nextTurn);
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

    private static void endGame(View view, Board board, GameDao gameDao) {
        displayEndingResult(view, board);
        gameDao.endGame();
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
