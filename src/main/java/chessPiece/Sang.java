package chessPiece;

public class Sang extends ChessPiece {

    public Sang(final String name, final BoardPosition boardPosition) {
        super(name, boardPosition);
    }

    @Override
    public Sang move(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 3 && Math.abs(dy) == 2) {
            return new Sang("상", boardPosition);
        }

        if (dy == 3 && Math.abs(dx) == 2) {
            return new Sang("상", boardPosition);
        }

        if (dx == -3 && Math.abs(dy) == 2) {
            return new Sang("상", boardPosition);
        }

        if (dy == -3 && Math.abs(dx) == 2) {
            return new Sang("상", boardPosition);
        }

        throw new IllegalArgumentException("[ERROR] 상이 움직일 수 없는 위치입니다.");

    }

}
