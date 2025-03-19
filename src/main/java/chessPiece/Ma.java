package chessPiece;

public class Ma extends ChessPiece {

    public Ma(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public void move(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 2 && Math.abs(dy) == 1) {
            this.boardPosition = boardPosition;
            return;
        }

        if (dy == 2 && Math.abs(dx) == 1) {
            this.boardPosition = boardPosition;
            return;
        }

        if (dx == -2 && Math.abs(dy) == 1) {
            this.boardPosition = boardPosition;
            return;
        }

        if (dy == -2 && Math.abs(dx) == 1) {
            this.boardPosition = boardPosition;
            return;
        }

        throw new IllegalArgumentException("[ERROR] 마가 움직일 수 없는 위치입니다.");

    }

}
