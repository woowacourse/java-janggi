package domain.board;

import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> initializeBoard) {
        this.board = initializeBoard;
    }

    public boolean isEmpty(Position position) {
        return getPiece(position).isNeutral();
    }

    public Piece getPiece(Position position) {
        return board.get(position);
    }

    public void validateStartPosition(Position start, Side turn) {
        start.validateRange();
        validateEnsureSameSidePiece(start, turn);
    }

    public void movePiece(Position start, Position destination) {
        board.put(destination, board.get(start));
        board.put(start, EmptyPiece.getInstance());
    }

    private void validateEnsureSameSidePiece(Position start, Side turn) {
        if (!getPiece(start).isSameSide(turn)) {
            throw new IllegalArgumentException("아군 기물만 이동 가능합니다. 다시 입력해주세요.");
        }
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
