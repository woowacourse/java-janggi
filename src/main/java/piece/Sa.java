package piece;

import static pieceProperty.PieceType.SA;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;

public class Sa extends Piece {

    public Sa(final Position position) {
        super(position);
    }

    @Override
    public boolean canMoveTo(final Position position) {
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();

        if ((Math.abs(dx) == 1 && dy == 0) || (Math.abs(dy) == 1 && dx == 0)) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 사가 움직일 수 없는 위치 입니다.");
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
        return SA;
    }

}
