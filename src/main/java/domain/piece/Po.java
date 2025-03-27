package domain.piece;

import domain.Team;

public class Po extends RangeMovePiece {

    public static final int SCORE = 7;

    public Po(Team team) {
        super(team);
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
