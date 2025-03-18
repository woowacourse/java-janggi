package domain.piece;

import domain.Team;

public class Elephant extends Piece{

    public Elephant(Team team) {
        super(team);
    }

    @Override
    protected boolean canMove(int startRow, int startColumn, int targetRow, int targetColumn) {
        return false;
    }
}
