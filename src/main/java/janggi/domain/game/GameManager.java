package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.Destinations;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public class GameManager {

    private final Players players;
    private final Board board;
    private Turn turn; // 역순 TDA - 꺼내서 사용하던 Turn 을 Players 필드로

    public GameManager(Players players, Board board, Turn initiativeTurn) {
        this.players = players;
        this.board = board;
        this.turn = initiativeTurn;
    }

    public void switchTurn() {
        turn = turn.next();
    }

    public boolean isFinished() {
        return !board.isBothPalaceExist();
    }

    public Player currentPlayer() {
        return players.currentPlayer(turn.currentSide());
    }

    public boolean isThereMoveablePiece(Position selectedPosition) {
        if (board.isPieceExist(selectedPosition)) {
            Piece selectedPiece = board.findPieceBy(selectedPosition);
            Side currentSide = turn.currentSide();
            return players.isCurrentSidePiece(currentSide, selectedPiece) && board.isMoveablePiece(selectedPosition);
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
        return board.calculateScore(turn.currentSide());
    }

    public Map<Side, String> getPlayersInfo() {
        return players.getPlayersInfo();
    }

    public Side getCurrentSide() {
        return turn.currentSide();
    }

    public Map<Position, Piece> getPiecePositions() {
        return board.piecePosition();
    }

    public Board getBoard() {
        return board;
    }
}
