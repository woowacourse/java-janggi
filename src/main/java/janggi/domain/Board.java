package janggi.domain;

import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public Map<Position, Piece> getBoard() {
        return new HashMap<>(board);
    }

    public void movePiece(final Team team, final Position beforePosition, final Position afterPosition) {
        Piece piece = board.get(beforePosition);

        if (!piece.is(team)) {
            throw new IllegalArgumentException("지금은 " + team.getName() + "팀 기물만 이동할 수 있습니다.");
        }

        piece.getMovableValidator(beforePosition, afterPosition).accept(new Pieces(board));
        board.put(beforePosition, new None());
        board.put(afterPosition, piece);
    }
}
