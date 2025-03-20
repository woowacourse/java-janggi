package chessPiece;

public class Ma extends ChessPiece {

    public Ma(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public boolean isMove(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 2 && Math.abs(dy) == 1) {
            return true;
        }

        if (dy == 2 && Math.abs(dx) == 1) {
            return true;
        }

        if (dx == -2 && Math.abs(dy) == 1) {
            return true;
        }

        if (dy == -2 && Math.abs(dx) == 1) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 마가 움직일 수 없는 위치입니다.");
    }

    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
