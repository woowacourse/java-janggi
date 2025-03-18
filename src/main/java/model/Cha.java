package model;

public class Cha extends Piece {

    public Cha(String team) {
        super(team);
    }

    @Override
    public boolean canMove(int beforeX, int beforeY, int afterX, int afterY) {
        return !((beforeX == afterX && beforeY == afterY) || (beforeX!=afterX && beforeY != afterY));
    }
}
