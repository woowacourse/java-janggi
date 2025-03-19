package janggi;

import janggi.piece.Piece;
import janggi.piece.Team;
import java.util.HashMap;
import java.util.Map;

public final class Board {

    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public void move(final Position start, final Position end) {
        if (!board.containsKey(start)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치에 기물이 없습니다.", start.getRowValue(), start.getColumnValue()));
        }
        final Piece piece = board.get(start);
        if (!piece.canMove(start, end, this)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치로 이동할 수 없습니다.", end.getRowValue(), end.getColumnValue()));
        }
        board.remove(end);
        board.remove(start);
        board.put(end, piece);
    }

    public boolean isPresentSameTeam(final Team team, final Position position) {
        if (board.containsKey(position)) {
            final Piece piece = board.get(position);
            return piece.isSameTeam(team);
        }
        return false;
    }

    public boolean isPresent(final Position position) {
        return board.containsKey(position);
    }
}
