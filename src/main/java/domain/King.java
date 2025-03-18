package domain;

public class King extends Piece{

    public King(Team team) {
        super(team);
    }

    @Override
    public boolean canMove(int startRow, int startColumn, int targetRow, int targetColumn) {
        return false;
    }
}
