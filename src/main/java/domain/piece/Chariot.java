package domain.piece;

import domain.Team;

public class Chariot extends Piece{

    public Chariot(Team team) {
        super(team);
    }

    @Override
    protected boolean canMove(int startRow, int startColumn, int targetRow, int targetColumn) {
        return false;
    }
}
