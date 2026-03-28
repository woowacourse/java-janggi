package janggi.domain.point;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;
import java.util.List;

public class Route {

    private final List<Piece> pieces;

    public Route(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isEmpty() {
        return pieces.isEmpty();
    }

    public boolean hasObstacle() {
        return pieces.size() > 1;
    }

    public boolean hasSameType(PieceType type) {
        return pieces.stream().anyMatch(piece -> !piece.isSameType(type));
    }

    public boolean hasAlly(Team team) {
        return pieces.stream().anyMatch(piece -> piece.isSameTeam(team));
    }
}
