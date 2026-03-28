package domain;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.Map;

public class Game {

    private final Board board;
    private Side turn;

    public Game(BoardInitializer boardInitializer) {
        this.board = new Board(boardInitializer.initialize());
        this.turn = boardInitializer.getFirstTurnSide();
    }

    public Piece getPiece(Position position) {
        validateStartPosition(position);
        return board.getPiece(position);
    }

    public boolean isAvailableDestination(Position destination) {
        if (board.isInvalidRange(destination)) {
            return false;
        }

        return isOpponentOrEmptyPiece(destination);
    }

    public boolean isOpponentPiece(Position position) {
        return isOpponentOrEmptyPiece(position) && !board.isEmpty(position);
    }

    public boolean isCannon(Position position) {
        return board.isInvalidRange(position) || board.isCannon(position);
    }

    public boolean isNotEmpty(Position position) {
        return !board.isEmpty(position);
    }

    public void validateStartPosition(Position start) {
        board.validateRange(start);
        validateCurrentTurnPiece(start);
    }

    public void move(Position start, Position destination) {
        validateCurrentTurnPiece(start);
        board.move(start, destination);
        changeTurn();
    }

    private boolean isFriendlyPiece(Position position) {
        return board.getPiece(position).isFriendly(turn);
    }

    private boolean isOpponentOrEmptyPiece(Position position) {
        return !isFriendlyPiece(position);
    }

    private void validateCurrentTurnPiece(Position start) {
        if (isOpponentOrEmptyPiece(start)) {
            throw new IllegalArgumentException("아군 기물만 이동 가능합니다.");
        }
    }

    private void changeTurn() {
        turn = turn.change();
    }

    public Side getTurn() {
        return turn;
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }
}
