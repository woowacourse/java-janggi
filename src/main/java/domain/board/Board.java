package domain.board;

import domain.coordinate.Position;
import domain.piece.Cannon;
import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.Arrays;
import java.util.Map;

public class Board {

    private static final BoardBounds BOUNDS = BoardBounds.JANGGI;

    private final Piece[][] board = new Piece[BOUNDS.colsize()][BOUNDS.rowSize()];

    public Board(Map<Position, Piece> initialize) {
        for (Position position : BOUNDS.allPositions()) {
            board[position.col()][position.row()] = initialize.getOrDefault(position, EmptyPiece.getInstance());
        }
    }

    public boolean isEmpty(Position position) {
        return !isInvalidRange(position) && getPiece(position).isEmpty();
    }

    public boolean isCannon(Position position) {
        return board[position.col()][position.row()] instanceof Cannon;
    }

    public void move(Position start, Position destination) {
        validateRange(start);
        validateRange(destination);
        board[destination.col()][destination.row()] = board[start.col()][start.row()];
        board[start.col()][start.row()] = EmptyPiece.getInstance();
    }

    public Piece getPiece(Position position) {
        validateRange(position);
        return board[position.col()][position.row()];
    }

    public boolean isInvalidRange(Position position) {
        return !BOUNDS.contains(position);
    }

    public void validateRange(Position position) {
        BOUNDS.validateContains(position);
    }

    public Piece[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }
}
