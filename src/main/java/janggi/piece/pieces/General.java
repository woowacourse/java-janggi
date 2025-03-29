package janggi.piece.pieces;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public class General implements Piece {
    private final Team team;

    public General(Team team) {
        this.team = team;
    }

    @Override
    public List<Route> calculateRoutes(Position position) {
        return List.of();
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
