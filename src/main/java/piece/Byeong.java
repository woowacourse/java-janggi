package piece;

import static pieceProperty.PieceType.BYEONG;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;

public class Byeong extends Piece {

    public Byeong(final Position position) {
        super(position);
    }

    @Override
    public boolean isMove(final Position position) {
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();

        if (dx == 0 && Math.abs(dy) == 1 || dx == -1 && dy == 0) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 병이 움직일 수 없는 위치 입니다.");
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        return List.of(position);
    }

    @Override
    public void updateChessPiecePositionBy(Position position) {
        this.position = position;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return BYEONG;
    }

}
