package model.board;

import java.util.HashMap;
import java.util.Map;
import model.move.Move;
import model.pieces.Piece;
import model.position.Position;

public class Board {
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 10;
    private static final int MIN_COL = 1;
    private static final int MAX_COL = 9;
    private final Map<Position, Piece> board;

    public Board() {
        this.board = new HashMap<>();
    }

    public boolean isInside(Position position) {
        return position.row().value() >= MIN_ROW &&
                position.row().value() <= MAX_ROW &&
                position.column().value() >= MIN_COL &&
                position.column().value() <= MAX_COL;
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
            throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다.");
        }

        executeMove(move, piece);
    }

    private static void validatePieceExists(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 기물이 없습니다.");
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
        return findPiece(position) == null;
    }

    public Map<Position, Piece> board() {
        return board;
    }
}
