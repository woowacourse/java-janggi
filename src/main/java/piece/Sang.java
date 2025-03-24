package piece;

import static pieceProperty.PieceType.SANG;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;

public class Sang extends Piece {

    public Sang(final Position position) {
        super(position);
    }

    @Override
    public boolean canMoveTo(final Position position) {
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();

        if (dx == 3 && Math.abs(dy) == 2) {
            return true;
        }

        if (dy == 3 && Math.abs(dx) == 2) {
            return true;
        }

        if (dx == -3 && Math.abs(dy) == 2) {
            return true;
        }

        if (dy == -3 && Math.abs(dx) == 2) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 상이 움직일 수 없는 위치입니다.");
    }

    @Override
    public Positions makeRoute(final Position position) {
        Positions route = new Positions(List.of());

        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();
        int presentRow = getBoardPosition().getRow();
        int presentCol = getBoardPosition().getCol();

        if (dx == 3 && dy == 2) {
            route.addPosition(new Position(presentRow - 1, presentCol));
            route.addPosition(new Position(presentRow - 2, presentCol - 1));
            route.addPosition(new Position(presentRow - 3, presentCol - 2));
        }

        if (dx == 3 && dy == -2) {
            route.addPosition(new Position(presentRow - 1, presentCol));
            route.addPosition(new Position(presentRow - 2, presentCol + 1));
            route.addPosition(new Position(presentRow - 3, presentCol + 2));
        }

        if (dx == 2 && dy == -3) {
            route.addPosition(new Position(presentRow, presentCol + 1));
            route.addPosition(new Position(presentRow - 1, presentCol + 2));
            route.addPosition(new Position(presentRow - 2, presentCol + 3));
        }

        if (dx == -2 && dy == -3) {
            route.addPosition(new Position(presentRow, presentCol + 1));
            route.addPosition(new Position(presentRow + 1, presentCol + 2));
            route.addPosition(new Position(presentRow + 2, presentCol + 3));
        }

        if (dx == -3 && dy == 2) {
            route.addPosition(new Position(presentRow + 1, presentCol));
            route.addPosition(new Position(presentRow + 2, presentCol - 1));
            route.addPosition(new Position(presentRow + 3, presentCol - 2));
        }

        if (dx == -3 && dy == -2) {
            route.addPosition(new Position(presentRow + 1, presentCol));
            route.addPosition(new Position(presentRow + 2, presentCol + 1));
            route.addPosition(new Position(presentRow + 3, presentCol + 2));
        }

        if (dx == 2 && dy == 3) {
            route.addPosition(new Position(presentRow, presentCol - 1));
            route.addPosition(new Position(presentRow - 1, presentCol - 2));
            route.addPosition(new Position(presentRow - 2, presentCol - 3));
        }

        if (dx == -2 && dy == 3) {
            route.addPosition(new Position(presentRow, presentCol - 1));
            route.addPosition(new Position(presentRow + 1, presentCol - 2));
            route.addPosition(new Position(presentRow + 2, presentCol - 3));
        }

        return route;
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
        return SANG;
    }
}
