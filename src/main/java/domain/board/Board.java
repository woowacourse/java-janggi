package domain.board;

import domain.Position;
import domain.piece.Cannon;
import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.Arrays;
import java.util.Map;

public class Board {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;
    private static final int POSITION_THRESHOLD = 0;

    private final Piece[][] board = new Piece[COL_SIZE][ROW_SIZE];

    public Board(Map<Position, Piece> initialize) {
        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                board[i][j] = initialize.getOrDefault(new Position(i, j), new EmptyPiece());
            }
        }
    }

    public boolean isEmpty(Position position) {
        return !isInvalidRange(position) && getPiece(position).isNeutral();
    }

    public boolean isCannon(Position position) {
        return board[position.col()][position.row()] instanceof Cannon;
    }

    public void move(Position start, Position destination) {
        validateRange(start);
        validateRange(destination);
        board[destination.col()][destination.row()] = board[start.col()][start.row()];
        board[start.col()][start.row()] = new EmptyPiece();
    }

    public Piece getPiece(Position position) {
        validateRange(position);
        return board[position.col()][position.row()];
    }

    public boolean isInvalidRange(Position position) {
        return position.col() < POSITION_THRESHOLD || position.col() >= COL_SIZE
                || position.row() < POSITION_THRESHOLD || position.row() >= ROW_SIZE;
    }

    public void validateRange(Position position) {
        if (position.col() < POSITION_THRESHOLD || position.col() >= COL_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 열 좌표: %d (열 좌표는 0 에서 9 사이여야 합니다.)", position.col()));
        }

        if (position.row() < POSITION_THRESHOLD || position.row() >= ROW_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 행 좌표: %d (행 좌표는 0 에서 8 사이여야 합니다.)", position.row()));
        }
    }

    public Piece[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }
}
