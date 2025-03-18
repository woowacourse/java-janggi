package domain.piece;

import domain.Team;

public class Guard extends Piece{

    public Guard(Team team) {
        super(team);
    }

    @Override
    protected boolean canMove(int startRow, int startColumn, int targetRow, int targetColumn) {
        return false;
    }
}
