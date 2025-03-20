package chessPiece;

public class Jol extends ChessPiece {

    public Jol(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public boolean isMove(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 0 && Math.abs(dy) == 1 || dx == 1 && dy == 0) {
            return true;
        }
        throw new IllegalArgumentException("[ERROR] 병이 움직일 수 없는 위치입니다.");
    }

    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
