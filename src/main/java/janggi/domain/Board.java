package janggi.domain;

import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public Piece getPieceByPosition(final Position position) {
        return board.get(position);
    }

    public Map<Position, Piece> getBoard() {
        return new HashMap<>(board);
    }

    public void movePiece(final Team team, final Position beforePosition, final Position afterPosition) {
        Piece piece = board.get(beforePosition);

        if (!piece.getTeam().equals(team)) {
            throw new IllegalArgumentException("지금은 " + team.getName() + "팀 기물만 이동할 수 있습니다.");
        }
        if (piece.isNone()) {
            throw new IllegalArgumentException("해당 위치에 이동시킬 기물이 존재하지 않습니다.");
        }

        piece.getMovableValidator(beforePosition, afterPosition).accept(new HashMap<>(board));
        board.put(beforePosition, new None());
        board.put(afterPosition, piece);
    }
}
