package piece;

import static pieceProperty.PieceType.JANGGUN;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public class Janggun extends Piece {

    public Janggun(final Position position) {
        super(position);
    }

    @Override
    public boolean canMoveTo(final Position position) {
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();

        if (Math.abs(dx) == 1 && dy == 0 || Math.abs(dy) == 1 && dx == 0) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 왕이 움직일 수 없는 위치 입니다.");
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
        return true;
    }

    @Override
    public PieceType getPieceType() {
        return JANGGUN;
    }

}
