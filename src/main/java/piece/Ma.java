package piece;

import static pieceProperty.PieceType.MA;

import java.util.ArrayList;
import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;

public class Ma extends Piece {

    public Ma(final Position position) {
        super(position);
    }

    @Override
    public boolean isMove(final Position position) {
        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();

        if (dx == 2 && Math.abs(dy) == 1) {
            return true;
        }

        if (dy == 2 && Math.abs(dx) == 1) {
            return true;
        }

        if (dx == -2 && Math.abs(dy) == 1) {
            return true;
        }

        if (dy == -2 && Math.abs(dx) == 1) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 마가 움직일 수 없는 위치입니다.");
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        List<Position> route = new ArrayList<>();

        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();
        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (dx == 2 && dy == 1) {
            route.add(new Position(presentRow - 1, presentCol));
            route.add(new Position(presentRow - 2, presentCol - 1));
        }

        if (dx == 2 && dy == -1) {
            route.add(new Position(presentRow - 1, presentCol));
            route.add(new Position(presentRow - 2, presentCol + 1));
        }

        if (dx == 1 && dy == -2) {
            route.add(new Position(presentRow, presentCol + 1));
            route.add(new Position(presentRow - 1, presentCol + 2));
        }

        if (dx == -1 && dy == -2) {
            route.add(new Position(presentRow, presentCol + 1));
            route.add(new Position(presentRow + 1, presentCol + 2));
        }

        if (dx == -2 && dy == -1) {
            route.add(new Position(presentRow + 1, presentCol));
            route.add(new Position(presentRow + 2, presentCol + 1));
        }

        if (dx == -2 && dy == 1) {
            route.add(new Position(presentRow + 1, presentCol));
            route.add(new Position(presentRow + 2, presentCol - 1));
        }

        if (dx == 1 && dy == 2) {
            route.add(new Position(presentRow, presentCol - 1));
            route.add(new Position(presentRow - 1, presentCol - 2));
        }

        if (dx == -1 && dy == 2) {
            route.add(new Position(presentRow, presentCol - 1));
            route.add(new Position(presentRow + 1, presentCol - 2));
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
    public PieceType getPieceType() {
        return MA;
    }

}
