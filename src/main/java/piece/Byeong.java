package piece;

import static pieceProperty.PieceType.BYEONG;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public class Byeong extends Piece {

    public Byeong(final Position position) {
        super(position);
    }

    @Override
    public boolean canMoveTo(final Position position) {
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();

        if (dx == 0 && Math.abs(dy) == 1 || dx == -1 && dy == 0) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 병이 움직일 수 없는 위치 입니다.");
    }

    @Override
    public Positions makeRoute(final Position position) {
        return new Positions(List.of(position));
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
    public boolean isPo() {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return BYEONG;
    }

}
