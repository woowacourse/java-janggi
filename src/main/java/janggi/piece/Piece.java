package janggi.piece;

import janggi.board.Board;
import janggi.moveStrategy.MoveStrategy;
import janggi.position.Position;

public sealed abstract class Piece permits Cannon, Chariot, Elephant, General, Guard, Horse, Soldier {

    protected final Team team;
    protected final MoveStrategy moveStrategy;

    protected Piece(final Team team, final MoveStrategy moveStrategy) {
        this.team = team;
        this.moveStrategy = moveStrategy;
    }

    public boolean canMove(final Position start, final Position end, final Board board) {
        return moveStrategy.canMove(start, end, team, board);
    }

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract Type getType();
}
