package chessPiece;

import java.util.List;

public class Byeong extends ChessPiece {

    public Byeong(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public boolean isMove(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 0 && Math.abs(dy) == 1 || dx == -1 && dy == 0) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 병이 움직일 수 없는 위치 입니다.");
    }

    @Override
    public List<BoardPosition> makeRoute(final BoardPosition boardPosition) {
        return List.of(boardPosition);
    }

    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
