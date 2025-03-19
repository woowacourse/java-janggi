package chessPiece;

public class Byeong extends ChessPiece {
    public Byeong(final String name, final BoardPosition boardPosition) {
        super(name, boardPosition);
    }

    @Override
    public ChessPiece move(final BoardPosition boardPosition) {

        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 0 && Math.abs(dy) == 1) {
            return new Byeong("병", boardPosition);
        }

        if (dx == -1 && dy == 0) {
            return new Byeong("병", boardPosition);
        }
        throw new IllegalArgumentException("[ERROR] 병이 움직일 수 없는 위치입니다.");
    }

}
