package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.Destinations;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public class GameManager {

    private final Players players;
    private final Board board;

    public GameManager(Players players, Board board) {
        this.players = players;
        this.board = board;
    }

    public void switchTurn() {
        players.nextTurn();
    }

    public boolean isFinished() {
        return !board.isBothPalaceExist();
    }

    public Player currentPlayer() {
        return players.currentPlayer();
    }

    public boolean isThereMoveablePiece(Position selectedPosition) {
        if (board.isPieceExist(selectedPosition)) {
            Piece selectedPiece = board.findPieceBy(selectedPosition);
            return players.isCurrentSidePiece(selectedPiece) && board.isMoveablePiece(selectedPosition);
        }
        return false;
    }

    public Destinations findDestinations(Position selectedPosition) {
        return board.moveablePositions(selectedPosition);
    }

    public boolean isPieceExist(Position position) {
        return board.isPieceExist(position);
    }

    public void movePiece(Position selected, Position target, Destinations destinations) {
        board.movePiece(selected, target, destinations);
    }

    public double currentPlayerScore() {
        return board.calculateScore(currentPlayer());
    }

    public Map<Side, String> getPlayersInfo() {
        return players.getPlayersInfo();
    }

    public Map<Position, Piece> getPiecePositions() {
        return board.piecePosition();
    }

    public Board getBoard() {
        return board;
    }
}
