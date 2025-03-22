package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.Type;
import janggi.position.Position;
import janggi.turn.ChoTurn;
import janggi.turn.Turn;
import java.util.HashMap;
import java.util.Map;

public final class Board {

    private final Map<Position, Piece> board;
    private Turn turn;

    public Board(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
        this.turn = new ChoTurn();
    }

    public void move(final Position start, final Position end) {
        validateStartPosition(start);
        final Piece piece = board.get(start);
        validateTurn(piece);
        validateRoute(start, end, piece);
        board.remove(end);
        board.remove(start);
        board.put(end, piece);
        turn = turn.changeTurn(this);
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
            return piece.getType() == Type.CANNON;
        }
        return false;
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

    private void validateTurn(final Piece piece) {
        if (!turn.isMovingSameTeam(piece)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] 다른 팀의 기물을 움직이고 있습니다."));
        }
    }

    private void validateRoute(final Position start, final Position end, final Piece piece) {
        if (!piece.canMove(start, end, this)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치로 이동할 수 없습니다.", end.getRowValue(), end.getColumnValue()));
        }
    }

    public String getTurn() {
        return turn.getTurnName();
    }
}
