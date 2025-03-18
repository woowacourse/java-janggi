package domain.piece;

import domain.Team;

public class Cannon extends Piece{

    public Cannon(Team team) {
        super(team);
    }

    @Override
    protected boolean canMove(int startRow, int startColumn, int targetRow, int targetColumn) {
        return false;
    }
}
