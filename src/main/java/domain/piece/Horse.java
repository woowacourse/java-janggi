package domain.piece;

import domain.Team;

public class Horse extends Piece{

    public Horse(Team team) {
        super(team);
    }

    @Override
    protected boolean canMove(int startRow, int startColumn, int targetRow, int targetColumn) {
        return false;
    }
}
