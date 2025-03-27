package domain.piece;

import domain.Team;

public class Cha extends RangeMovePiece {

    private static final int SCORE = 13;

    public Cha(Team team) {
        super(team);
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}