package chessPiece;

public class Byeong extends ChessPiece {

    public Byeong(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public void move(final BoardPosition boardPosition) {

        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 0 && Math.abs(dy) == 1) {
            this.boardPosition = boardPosition;
            return;
        }

        if (dx == -1 && dy == 0) {
            this.boardPosition = boardPosition;
            return;
        }
        throw new IllegalArgumentException("[ERROR] 병이 움직일 수 없는 위치입니다.");
    }

}
