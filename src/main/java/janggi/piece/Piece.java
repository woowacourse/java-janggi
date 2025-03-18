package janggi.piece;

import janggi.Position;
import janggi.Score;
import janggi.Team;

import java.util.List;

public abstract class Piece {

    protected final Position position;
    protected final Team team;

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public abstract List<Position> getRoute(Position destination);

    public abstract Piece move(Position destination);

    public abstract Score die();
}
