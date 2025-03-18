package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;

public abstract class Piece {

    protected final Position position;
    protected final Team team;

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    protected static int getRowByTeam(final int row, final Team team) {
        if (team == Team.GREEN) {
            return 11 - row;
        }
        return row;
    }

    public abstract Piece move(Board board, Position destination);

    public abstract Score die();

    public boolean isAlly(final Team team) {
        return this.team == team;
    }

    public Position getPosition() {
        return position;
    }
}
