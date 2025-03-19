package chessPiece;

public class Jol extends ChessPiece {

    public Jol(final String name, final BoardPosition boardPosition) {
        super(name, boardPosition);
    }

    @Override
    public Jol move(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 0 && Math.abs(dy) == 1) {
            return new Jol("졸", boardPosition);
        }

        if (dx == 1 && dy == 0) {
            return new Jol("졸", boardPosition);
        }
        throw new IllegalArgumentException("[ERROR] 병이 움직일 수 없는 위치입니다.");
    }
}
