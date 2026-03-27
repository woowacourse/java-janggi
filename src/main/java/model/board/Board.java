package model.board;

import model.move.Move;
import model.pieces.Piece;

import java.util.HashMap;
import java.util.Map;
import model.position.Position;

public class Board {
    private Map<Position, Piece> board;

    public Board() {
        this.board = new HashMap<>();
    }

    public void place(Position position, Piece piece) {
        board.put(position, piece);
    }

    public void remove(Position position) {
        board.remove(position);
    }

    public void move(Move move) {
        Piece piece = findPiece(move.from());
        validatePieceExists(piece);

        if (!piece.canMove(move, this)) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }

        executeMove(move, piece);
    }

    private static void validatePieceExists(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("기물이 없습니다.");
        }
    }

    private void executeMove(Move move, Piece piece) {
        place(move.to(), piece);
        remove(move.from());
    }

    public boolean isPieceAt(Position position, Piece piece) {
        return piece.equals(board.get(position));
    }

    public Piece findPiece(Position position) {
        return board.get(position);
    }

    public boolean isPathEmpty(Position position) {
        if (findPiece(position) == null) {
            return true;
        }
        return false;
    }
}
