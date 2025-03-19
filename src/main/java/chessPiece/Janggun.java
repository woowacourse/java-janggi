package chessPiece;

public class Janggun extends ChessPiece {

    public Janggun(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public void move(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (Math.abs(dx) == 1 && dy == 0) {
            this.boardPosition = boardPosition;
            return;
        }

        if (Math.abs(dy) == 1 && dx == 0) {
            this.boardPosition = boardPosition;
            return;
        }

        throw new IllegalArgumentException("[ERROR] 왕이 움직일 수 없는 위치 입니다.");
    }
}
