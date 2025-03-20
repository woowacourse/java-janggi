package chessPiece;

public class Cha extends ChessPiece {

    public Cha(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public boolean isMove(BoardPosition boardPosition) {
        if ((super.getBoardPosition().getRow() == boardPosition.getRow())
                || (super.getBoardPosition().getCol() == boardPosition.getCol())) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 차가 움직일 수 없는 위치 입니다.");
    }

    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
