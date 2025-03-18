package domain;

public class 졸병 extends Piece {

    private final Side side;

    public 졸병(Side side) {
        super(2);
        this.side = side;
    }

    @Override
    public boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        if (side == Side.초) {
            return (Math.abs(beforeColumn - afterColumn) == 1 && beforeRow == afterRow) || (afterRow - beforeRow == -1
                    && afterColumn == beforeColumn);
        }
        return (Math.abs(beforeColumn - afterColumn) == 1 && beforeRow == afterRow) || (afterRow - beforeRow == 1
                && afterColumn == beforeColumn);
    }
}
