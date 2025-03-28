package domain.piece;

import domain.Position;
import domain.Team;

public class Cha extends RangeMovePiece {

    private static final int SCORE = 13;

    public Cha(Team team, Position position) {
        super(team, position);
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}