package janggi.domain.piece;

import janggi.domain.BoardView;
import janggi.domain.moveRule.MoveRule;
import janggi.domain.vo.Position;

public abstract class Piece {
    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public boolean isSameTeam(Team otherTeam) {
        return team == otherTeam;
    }

    public boolean isEmpty() {
        return false;
    }

    public Team findTeam() {
        return team;
    }

    public boolean canMove(Position from, Position to, BoardView board) {
        return moveRule().canMove(from, to, board);
    }

    protected abstract MoveRule moveRule();

    public String display() {
        return team.findPrefix() + toString();
    }


    @Override
    public abstract String toString();

    public abstract int score();

}
