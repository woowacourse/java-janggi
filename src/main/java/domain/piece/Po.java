package domain.piece;

import domain.Position;
import domain.Team;

public class Po extends RangeMovePiece {

    public static final int SCORE = 7;

    public Po(Team team, Position position) {
        super(team, position);
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
