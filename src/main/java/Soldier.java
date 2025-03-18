public class Soldier extends Piece {

    public Soldier(PieceColor color) {
        super(color);
    }

    public boolean canMove(int i, int i1, int i2, int i3) {
        int dx = i2 - i;
        int dy = i3 - i1;

        if (color == PieceColor.RED && (dx == 0 && dy == 1 || dx == 0 && dy == -1 || dx == 1 && dy == 0)) {
                return true;

            }

        if(color == PieceColor.BLUE && (dx==-1&&dy==0 || dx==0 && dy==1 || dx==1 && dy==-1)) {
                return true;
            }
        return false;
    }
}
