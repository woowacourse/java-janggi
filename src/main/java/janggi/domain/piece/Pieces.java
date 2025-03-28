package janggi.domain.piece;

import java.util.HashMap;
import java.util.Map;

public class Pieces {
    private final Map<Position, Piece> pieces;

    public Pieces(final Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public Piece get(final Position position) {
        return pieces.get(position);
    }

    public boolean isTeam(final Position position, final Team team) {
        return pieces.get(position).is(team);
    }
}