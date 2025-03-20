package chessPiece;

public class Sang extends ChessPiece {

    public Sang(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public boolean isMove(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 3 && Math.abs(dy) == 2) {
            return true;
        }

        if (dy == 3 && Math.abs(dx) == 2) {
            return true;
        }

        if (dx == -3 && Math.abs(dy) == 2) {
            return true;
        }

        if (dy == -3 && Math.abs(dx) == 2) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 상이 움직일 수 없는 위치입니다.");
    }

    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
