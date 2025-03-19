package chessPiece;

public class Sa extends ChessPiece {

    public Sa(final String name, final BoardPosition boardPosition) {
        super(name, boardPosition);
    }

    @Override
    public Sa move(final BoardPosition boardPosition) {

        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (Math.abs(dx) == 1 && dy == 0) {
            return new Sa("사", boardPosition);
        }

        if (Math.abs(dy) == 1 && dx == 0) {
            return new Sa("사", boardPosition);
        }

        throw new IllegalArgumentException("[ERROR] 사가 움직일 수 없는 위치 입니다.");
    }

}
