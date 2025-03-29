package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public class None implements Piece {
    private final Team team;

    public None(Team team) {
        this.team = team;
    }

    @Override
    public List<Route> calculateRoutes(Position start) {
        return List.of();
    }

    @Override
    public PieceType getType() {
        return PieceType.NONE;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
