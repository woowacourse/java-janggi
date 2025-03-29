package janggi.piece;

import janggi.position.Board;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.util.Set;

public abstract class Piece {
    private final Team team;

    protected Piece(Team team) {
        this.team = team;
    }

    public Team team() {
        return team;
    }

    public Row row() {
        return position().row();
    }

    public Column column() {
        return position().column();
    }

    public boolean isDifferentTeam(Team currentTeam) {
        return team != currentTeam;
    }

    public abstract Set<Position> possibleRoutes(Board board);

    public abstract Set<Position> possiblePalacePositions(Board board);

    public abstract Piece move(Team team, Position destination);

    public abstract PieceType type();

    public abstract Position position();

    public double score() {
        return type().score();
    }

}
