package chessPiece;

public class Po extends ChessPiece {

    public Po(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public void move(final BoardPosition boardPosition) {
        if (super.getBoardPosition().getRow() == boardPosition.getRow()
                || super.getBoardPosition().getCol() == boardPosition.getCol()) {
            this.boardPosition = boardPosition;
            return;
        }
        throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
    }

}
