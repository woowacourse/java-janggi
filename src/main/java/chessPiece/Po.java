package chessPiece;

public class Po extends ChessPiece {

    public Po(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public boolean isMove(final BoardPosition boardPosition) {
        if (super.getBoardPosition().getRow() == boardPosition.getRow()
                || super.getBoardPosition().getCol() == boardPosition.getCol()) {
            return true;
        }
        throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
    }

    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }
}
