package piece;

import static pieceProperty.PieceType.PO;

import java.util.ArrayList;
import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public class Po extends Piece {

    public Po(final Position position) {
        super(position);
    }

    @Override
    public Positions makeRoute(final Position position) {
        Positions route = new Positions(List.of());
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();
        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (dx == 0 && dy > 0) {
            for (int i = 1; i <= dy; i++) {
                route.addPosition(new Position(presentRow, presentCol - i));
            }
        }

        if (dx == 0 && dy < 0) {
            for (int i = 1; i <= Math.abs(dy); i++) {
                route.addPosition(new Position(presentRow, presentCol + i));
            }
        }

        if (dx > 0 && dy == 0) {
            for (int i = 1; i <= dx; i++) {
                route.addPosition(new Position(presentRow - i, presentCol));
            }
        }

        if (dx < 0 && dy == 0) {
            for (int i = 1; i <= Math.abs(dx); i++) {
                route.addPosition(new Position(presentRow + i, presentCol));
            }
        }

        return route;
    }

    @Override
    public boolean canMoveTo(final Position position) {
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

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public PieceType getPieceType() {
        return PO;
    }
}
