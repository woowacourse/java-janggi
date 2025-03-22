package piece;

import java.util.List;

public class Janggun extends Piece {

    public Janggun(final PieceProfile pieceProfile, final Position position) {
        super(pieceProfile, position);
    }

    @Override
    public boolean isMove(final Position position) {
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();

        if (Math.abs(dx) == 1 && dy == 0 || Math.abs(dy) == 1 && dx == 0) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 왕이 움직일 수 없는 위치 입니다.");
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        return List.of(position);
    }

    @Override
    public void updateChessPiecePositionBy(Position position) {
        this.position = position;
    }

}
