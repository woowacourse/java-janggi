package janggi.board;

import janggi.piece.Piece;
import janggi.position.Position;
import java.util.HashMap;
import java.util.Map;

public final class Board {

    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void move(final Position start, final Position end) {
        validateStartPosition(start);
        final Piece piece = board.get(start);
        validateEndPosition(end, piece);
        piece.validateMove(start, end, board);

        board.remove(end);
        board.remove(start);
        board.put(end, piece);
    }

    private void validateStartPosition(final Position start) {
        if (!board.containsKey(start)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치에 기물이 없습니다.", start.getRowValue(), start.getColumnValue()));
        }
    }

    private void validateEndPosition(final Position end, final Piece piece) {
        if (!board.containsKey(end)) {
            return;
        }
        if (piece.isSameTeam(board.get(end))) {
            throw new IllegalArgumentException("[ERROR] 같은 팀의 기물은 잡을 수 없습니다.");
        }
    }

    public boolean isExistPiece(final Position position) {
        return board.containsKey(position);
    }

    public Piece getPiece(final Position position) {
        return board.get(position);
    }
}
