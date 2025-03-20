package chessPiece;

import java.util.List;

public class Janggun extends ChessPiece {

    public Janggun(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public boolean isMove(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (Math.abs(dx) == 1 && dy == 0 || Math.abs(dy) == 1 && dx == 0) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 왕이 움직일 수 없는 위치 입니다.");
    }

    @Override
    public List<BoardPosition> makeRoute(final BoardPosition boardPosition) {
        return List.of(boardPosition);
    }

    @Override
    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
