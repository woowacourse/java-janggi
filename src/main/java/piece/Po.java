package piece;

import java.util.ArrayList;
import java.util.List;

public class Po extends Piece {

    public Po(final PieceProfile pieceProfile, final Position position) {
        super(pieceProfile, position);
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        List<Position> route = new ArrayList<>();
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();
        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (dx == 0 && dy > 0) {
            for (int i = 1; i <= dy; i++) {
                route.add(new Position(presentRow, presentCol - i));
            }
        }

        if (dx == 0 && dy < 0) {
            for (int i = 1; i <= Math.abs(dy); i++) {
                route.add(new Position(presentRow, presentCol + i));
            }
        }

        if (dx > 0 && dy == 0) {
            for (int i = 1; i <= dx; i++) {
                route.add(new Position(presentRow - i, presentCol));
            }
        }

        if (dx < 0 && dy == 0) {
            for (int i = 1; i <= Math.abs(dx); i++) {
                route.add(new Position(presentRow + i, presentCol));
            }
        }

        return route;
    }

    @Override
    public boolean isMove(final Position position) {
        if (super.getBoardPosition().getRow() == position.getRow()
                || super.getBoardPosition().getCol() == position.getCol()) {
            return true;
        }
        throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
    }

    @Override
    public void updateChessPiecePositionBy(Position position) {
        this.position = position;
    }
}
