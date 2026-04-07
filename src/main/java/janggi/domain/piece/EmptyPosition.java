package janggi.domain.piece;

import janggi.domain.moveRule.MoveRule;

public class EmptyPosition extends Piece {

    public EmptyPosition(Team team) {
        super(team);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public MoveRule moveRule() {
        return null;
    }

    @Override
    public int score() {
        return 0;
    }

    @Override
    public String display() {
        return "  ";
    }
}
