package janggi.piece;

import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Routes;
import java.util.Set;

public abstract class Piece {
    protected final Team team;
    protected final Position position;
    protected final Routes routes;

    protected Piece(Team team, Position position, Routes routes) {
        this.team = team;
        this.position = position;
        this.routes = routes;
    }

    protected void validateTeamOfPiece(Board board) {
        board.validateTeam(team);
    }

    public Position position() {
        return position;
    }

    public Team team() {
        return team;
    }

    public boolean isDifferentTeam(Team currentTeam) {
        return team != currentTeam;
    }

    public abstract Set<Position> possibleRoutes(Board board);

    public abstract Piece move(Team team, Position destination);

    public abstract PieceType type();
}
