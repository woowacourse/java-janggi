package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import java.util.Map;

public final class Game {
    private final long id;
    private final Board board;
    private Camp currentTurn;

    private Game(long id, Board board, Camp currentTurn) {
        this.id = id;
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public long id() {
        return id;
    }

    public static Game start(long id, Board board) {
        return new Game(id, board, Camp.CHO);
    }

    public static Game restore(long id, Board board, Camp currentTurn) {
        return new Game(id, board, currentTurn);
    }

    public Camp currentTurn() {
        return currentTurn;
    }

    public Map<Position, Piece> boardSnapshot() {
        return board.getBoard();
    }

    public Map<Camp, Double> calculateScore() {
        return board.calculateScore();
    }

    public void validateSourceForCurrentTurn(Position source) {
        board.validateCampTurn(source, currentTurn());
    }

    public boolean play(Position source, Position destination) {
        boolean gameEnded = board.movePiece(source, destination, currentTurn());
        if (!gameEnded) {
            currentTurn = currentTurn.next();
        }
        return gameEnded;
    }
}
