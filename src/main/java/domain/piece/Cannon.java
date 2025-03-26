package domain.piece;

import domain.Team;

public class Cannon extends RangeMovePiece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public boolean isCanon() {
        return true;
    }
    
}
