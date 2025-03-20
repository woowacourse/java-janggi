package chessPiece;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<BoardPosition> makeRoute(final BoardPosition boardPosition) {
        List<BoardPosition> route = new ArrayList<>();
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();
        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (dx == 0 && dy > 0) {
            for (int i = 1; i <= dy; i++) {
                route.add(new BoardPosition(presentRow, presentCol - i));
            }
        }

        if (dx == 0 && dy < 0) {
            for (int i = 1; i <= Math.abs(dy); i++) {
                route.add(new BoardPosition(presentRow, presentCol + i));
            }
        }

        if (dx > 0 && dy == 0) {
            for (int i = 1; i <= dx; i++) {
                route.add(new BoardPosition(presentRow - i, presentCol));
            }
        }

        if (dx < 0 && dy == 0) {
            for (int i = 1; i <= Math.abs(dx); i++) {
                route.add(new BoardPosition(presentRow + i, presentCol));
            }
        }

        return route;
    }

    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
