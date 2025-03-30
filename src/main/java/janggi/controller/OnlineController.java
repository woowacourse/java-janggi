package janggi.controller;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.board.Judge;
import janggi.board.point.Point;
import janggi.data.dao.BoardDao;
import janggi.data.dao.CampDao;
import janggi.data.dao.PieceDao;
import janggi.data.dao.PieceSymbolDao;
import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.piece.PieceSymbol;
import janggi.view.PointParser;
import janggi.view.View;
import java.util.Map;

public final class OnlineController implements Controller {

    private final View view;
    private final CampDao campDao;
    private final PieceSymbolDao pieceSymbolDao;
    private final BoardDao boardDao;
    private final PieceDao pieceDao;

    public OnlineController(View view,
                            CampDao campDao,
                            PieceSymbolDao pieceSymbolDao,
                            BoardDao boardDao,
                            PieceDao pieceDao) {
        this.view = view;
        this.campDao = campDao;
        this.pieceSymbolDao = pieceSymbolDao;
        this.boardDao = boardDao;
        this.pieceDao = pieceDao;
    }

    @Override
    public void gameStart() {
        view.displayStartBanner();
        view.displayOnlineModeBanner();
        boolean startGame = view.readStartGame();
        if (startGame) {
            initializeData();
            playGame();
        }
    }

    private void initializeData() {
        pieceSymbolDao.saveAll(PieceSymbol.values());
        campDao.saveAll(Camp.values());
    }

    private void playGame() {
        Board board = initializeBoard();
        Judge judge = new Judge();
        Camp currentTurnCamp = boardDao.findCurrentTurnCamp();
        while (!judge.isGameOver(board)) {
            displayCurrentBoard(board, judge);
            playTurnUntilSuccess(currentTurnCamp, board);
            currentTurnCamp = turnChange(currentTurnCamp);
        }
        displayGameEnd(board, judge);
        boardDao.endGame();
    }

    private Board initializeBoard() {
        if (boardDao.existsActiveGame() && isContinueGame()) {
            return loadBoard();
        }
        return createNewBoard();
    }

    private boolean isContinueGame() {
        if (view.readContinueGame(boardDao.findCurrentBoardCreatedAt())) {
            return true;
        }
        boardDao.endGame();
        return false;
    }

    private Board loadBoard() {
        Map<Point, Piece> placedPieces = boardDao.findCurrentBoardPieces();
        Board board = new Board();
        for (Point point : placedPieces.keySet()) {
            Piece piece = placedPieces.get(point);
            board.placePiece(point, piece);
        }
        return board;
    }

    private Board createNewBoard() {
        Board board = BoardGenerator.generate();
        boardDao.create();
        for (Point point : board.getPlacedPieces().keySet()) {
            Piece piece = board.getPlacedPieces().get(point);
            pieceDao.save(point, piece);
        }
        return board;
    }

    private Camp turnChange(Camp currentTurnCamp) {
        boardDao.turnChange();
        return currentTurnCamp.reverse();
    }

    private void playTurnUntilSuccess(Camp currentTurnCamp, Board board) {
        try {
            playTurn(currentTurnCamp, board);
        } catch (IllegalArgumentException e) {
            view.displayErrorMessage(e.getMessage());
            playTurnUntilSuccess(currentTurnCamp, board);
        }
    }

    private void playTurn(Camp baseCamp, Board board) {
        view.displayCurrentTurnCamp(baseCamp);
        Point fromPoint = PointParser.parse(view.readMoveFromPoint());
        Point toPoint = PointParser.parse(view.readMoveToPoint());
        validateSelectedPiece(board, fromPoint, baseCamp);
        movePiece(board, fromPoint, toPoint);
    }

    private void movePiece(Board board, Point fromPoint, Point toPoint) {
        board.movePiece(fromPoint, toPoint);
        pieceDao.delete(toPoint);
        pieceDao.move(fromPoint, toPoint);
    }

    private void validateSelectedPiece(Board board, Point from, Camp baseCamp) {
        Piece piece = board.getPiece(from);
        piece.validateSelect(baseCamp);
    }

    private void displayCurrentBoard(Board board, Judge judge) {
        view.displayPoint(judge.calculateScore(board));
        view.displayBoard(board.getPlacedPieces());
    }

    private void displayGameEnd(Board board, Judge judge) {
        displayCurrentBoard(board, judge);
        view.displayEndBanner();
    }
}
