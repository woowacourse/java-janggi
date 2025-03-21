package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MovingRules;

public abstract class Piece {

    protected final Team team;
    protected final MovingRules movingRules;

    public Piece(final Team team, final MovingRules movingRules) {
        this.team = team;
        this.movingRules = movingRules;
    }

    public abstract boolean canMove(final Position start, final Position end, final Board board);

    public abstract Type type();
    
    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }
}
