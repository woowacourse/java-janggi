package janggi;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
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
    public static final int GENERAL_PIECE_COUNT = 2;

    public static void main(String[] args) {
        View view = new View();
        DatabaseConfig DBConfig = new DatabaseConfig(
                "localhost:13306", "janggi",
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root", "root");
        DatabaseConnector DBConnector = new DatabaseConnector(DBConfig);
        GameDao gameDao = new GameDao(DBConnector);
        PieceDao pieceDao = new PieceDao(DBConnector);
        view.displayStartBanner();
        boolean startGame = view.readStartGame();
        if (startGame) {
            playGame(view, gameDao, pieceDao);
        }
    }

    private static void playGame(View view, GameDao gameDao, PieceDao pieceDao) {
        Board board = initializeIfNewGame(gameDao);
        Camp currentTurnCamp = gameDao.findLatestTurn();
        while (!isGameOver(pieceDao)) {
            view.displayBoard(pieceDao);
            if (view.readGameCommand(currentTurnCamp).equals("end")) {
                break;
            }
            currentTurnCamp = handleTurn(view, gameDao, board, currentTurnCamp, pieceDao);
        }
        if (isGameOver(pieceDao)) {
            endGame(view, board, gameDao, pieceDao);
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

    private static boolean isGameOver(PieceDao pieceDao) {
        return pieceDao.getGeneralCount() != GENERAL_PIECE_COUNT;
    }

    private static Camp handleTurn(View view, GameDao gameDao, Board board, Camp currentTurnCamp, PieceDao pieceDao) {
        boolean turnPlayed = tryPlayTurn(view, board, currentTurnCamp, pieceDao);
        if (!turnPlayed) {
            return currentTurnCamp;
        }
        Camp nextTurn = currentTurnCamp.reverse();
        gameDao.updateTurn(nextTurn);
        return nextTurn;
    }

    private static boolean tryPlayTurn(View view, Board board, Camp currentTurnCamp, PieceDao pieceDao) {
        try {
            String fromPointInput = view.readFromPoint();
            String toPointInput = view.readToPoint();
            executeTurn(fromPointInput, toPointInput, currentTurnCamp, board, pieceDao);
            return true;
        } catch (IllegalArgumentException e) {
            view.displayErrorMessage(e.getMessage());
            return false;
        }
    }

    private static void executeTurn(String fromPointInput, String toPointInput, Camp baseCamp, Board board,
                                    PieceDao pieceDao) {
        Point from = new Point(fromPointInput);
        Point to = new Point(toPointInput);
        Piece movingPiece = pieceDao.findByPoint(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
        validateSelectedPiece(baseCamp, movingPiece);
        board.move(from, to);
        pieceDao.deletePieceByPoint(from);
        pieceDao.upsertPiece(to, movingPiece);
    }


    private static void validateSelectedPiece(Camp baseCamp, Piece movingPiece) {
        movingPiece.validateSelect(baseCamp);
    }

    private static void endGame(View view, Board board, GameDao gameDao, PieceDao pieceDao) {
        displayEndingResult(view, board, pieceDao);
        gameDao.endGame();
        resetBoard(pieceDao);
    }

    private static void resetBoard(PieceDao pieceDao) {
        pieceDao.clearTable();
    }

    private static void displayEndingResult(View view, Board board, PieceDao pieceDao) {
        view.displayBoard(pieceDao);
        Camp winningCamp = pieceDao.findWinningCamp()
                .orElseThrow(() -> new IllegalStateException("승리한 캠프가 존재하지 않습니다."));
        view.displayEndingMessage(winningCamp);
        view.displayScore(Camp.CHU, board.calculateChuScore());
        view.displayScore(Camp.HAN, board.calculateHanScore());
    }
}
