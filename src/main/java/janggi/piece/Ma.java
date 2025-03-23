package janggi.piece;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Ma extends Piece {

    public Ma(final PieceProfile pieceProfile, final Position position) {
        super(pieceProfile, position);
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

    public void updateChessPiecePositionBy(Position position) {
        this.position = position;
    }

}
