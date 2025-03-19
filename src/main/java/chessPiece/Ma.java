package chessPiece;

public class Ma extends ChessPiece {

    public Ma(final String name, final BoardPosition boardPosition) {
        super(name, boardPosition);
    }

    @Override
    public Ma move(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 2 && Math.abs(dy) == 1) {
            return new Ma("마", boardPosition);
        }

        if (dy == 2 && Math.abs(dx) == 1) {
            return new Ma("마", boardPosition);
        }

        if (dx == -2 && Math.abs(dy) == 1) {
            return new Ma("마", boardPosition);
        }

        if (dy == -2 && Math.abs(dx) == 1) {
            return new Ma("마", boardPosition);
        }

        throw new IllegalArgumentException("[ERROR] 마가 움직일 수 없는 위치입니다.");

    }

}
