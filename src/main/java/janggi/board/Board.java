package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.Type;
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
        validateRoute(start, end, piece);
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

    public boolean isExistCannon(final Position position) {
        if (board.containsKey(position)) {
            final Piece piece = board.get(position);
            return piece.type() == Type.CANNON;
        }
        return false;
    }

    public boolean isExistPiece(final Position position) {
        return board.containsKey(position);
    }

    public Piece getPiece(final Position position) {
        return board.get(position);
    }

    private void validateStartPosition(final Position start) {
        if (!board.containsKey(start)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치에 기물이 없습니다.", start.getRowValue(), start.getColumnValue()));
        }
    }

    private void validateRoute(final Position start, final Position end, final Piece piece) {
        if (!piece.canMove(start, end, this)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치로 이동할 수 없습니다.", end.getRowValue(), end.getColumnValue()));
        }
    }
}
