package janggi.controller;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.board.Judge;
import janggi.board.point.Point;
import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.view.PointParser;
import janggi.view.View;

public final class OfflineController implements Controller {

    private static final Camp FIRST_TURN_CAMP = Camp.CHU;

    private final View view;

    public OfflineController(View view) {
        this.view = view;
    }

    @Override
    public void gameStart() {
        view.displayStartBanner();
        view.displayOfflineModeBanner();
        boolean startGame = view.readStartGame();
        if (startGame) {
            playGame();
        }
    }

    private void playGame() {
        Board board = BoardGenerator.generate();
        Judge judge = new Judge();
        Camp currentTurnCamp = FIRST_TURN_CAMP;
        while (!judge.isGameOver(board)) {
            displayCurrentBoard(board, judge);
            playTurnUntilSuccess(currentTurnCamp, board);
            currentTurnCamp = currentTurnCamp.reverse();
        }
        displayGameEnd(board, judge);
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
        board.movePiece(fromPoint, toPoint);
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
