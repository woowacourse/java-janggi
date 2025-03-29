package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public record None(Team team) implements Piece {
    @Override
    public List<Route> calculateRoutes(Position start) {
        return List.of();
    }

    @Override
    public PieceType getType() {
        return PieceType.NONE;
    }
}
