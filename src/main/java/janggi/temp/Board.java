package janggi.temp;

import janggi.temp.piece.Piece;
import janggi.temp.position.Position;
import java.util.Map;

public final class Board {

    private final Map<Position, Piece> pieces;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece get(final Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치에 기물이 없습니다.", position.getRowValue(), position.getColumnValue()));
        }
        return pieces.get(position);
    }

    public boolean hasPieceAt(final Position position) {
        return pieces.containsKey(position);
    }

    public boolean hasSameTeam(final Position destination, final Team team) {
        return pieces.containsKey(destination) && get(destination).team() == team;
    }
}
