package chessPiece;

public class Janggun extends ChessPiece {

    public Janggun(final String name, final BoardPosition boardPosition) {
        super(name, boardPosition);
    }

    @Override
    public Janggun move(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (Math.abs(dx) == 1 && dy == 0) {
            return new Janggun("왕", boardPosition);
        }

        if (Math.abs(dy) == 1 && dx == 0) {
            return new Janggun("왕", boardPosition);
        }

        throw new IllegalArgumentException("[ERROR] 왕이 움직일 수 없는 위치 입니다.");
    }
}
