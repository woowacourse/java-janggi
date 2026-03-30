package domain.board;

import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.LinkedHashMap;
import java.util.Map;

public class Board {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;
    private static final int POSITION_THRESHOLD = 0;

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> initialize) {
        Map<Position, Piece> board = new LinkedHashMap<>();

        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                board.put(new Position(i, j), initialize.getOrDefault(new Position(i, j), EmptyPiece.getInstance()));
            }
        }

        this.board = board;
    }

    public void validateStartPosition(Position start, Side turn) {
        validateRange(start);
        validateEnsureSameSidePiece(start, turn);
    }

    public boolean isEmpty(Position position) {
        return isValidRange(position) && getPiece(position).isNeutral();
    }

    public boolean isSameSide(Position position, Side turn) {
        return getPiece(position).isSameSide(turn);
    }

    public boolean isValidRange(Position position) {
        return position.col() >= POSITION_THRESHOLD && position.col() < COL_SIZE
                && position.row() >= POSITION_THRESHOLD && position.row() < ROW_SIZE;
    }

    public void movePiece(Position start, Position destination) {
        validateRange(start);
        validateRange(destination);

        board.put(destination, board.get(start));
        board.put(start, EmptyPiece.getInstance());
    }

    public Piece getPiece(Position position) {
        validateRange(position);
        return board.get(position);
    }

    private void validateEnsureSameSidePiece(Position start, Side turn) {
        if (!isSameSide(start, turn)) {
            throw new IllegalArgumentException("아군 기물만 이동 가능합니다. 다시 입력해주세요.");
        }
    }

    private void validateRange(Position position) {
        if (position.col() < POSITION_THRESHOLD || position.col() >= COL_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 열 좌표: %d (열 좌표는 0 에서 9 사이여야 합니다.)", position.col()));
        }

        if (position.row() < POSITION_THRESHOLD || position.row() >= ROW_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 행 좌표: %d (행 좌표는 0 에서 8 사이여야 합니다.)", position.row()));
        }
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
