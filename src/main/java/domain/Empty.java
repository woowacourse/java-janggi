package domain;

public class Empty extends Piece {
    public Empty() {
        super(0, null);
    }

    @Override
    public boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        throw new IllegalStateException("움직일 말이 존재하지 않습니다.");
    }
}
