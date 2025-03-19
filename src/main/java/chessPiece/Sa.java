package chessPiece;

public class Sa extends ChessPiece {

    public Sa(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
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

        throw new IllegalArgumentException("[ERROR] 사가 움직일 수 없는 위치 입니다.");
    }

}
