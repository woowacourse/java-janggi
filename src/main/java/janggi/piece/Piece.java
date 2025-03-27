package janggi.piece;

import janggi.position.Board;
import janggi.position.Position;
import java.util.Set;

public abstract class Piece {
    private final Team team;

    protected Piece(Team team) {
        this.team = team;
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

    public abstract Position position();
}
