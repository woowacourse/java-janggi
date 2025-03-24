package janggi.piece;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sang extends Piece {

    public Sang(final PieceProfile pieceProfile, final Position position) {
        super(pieceProfile, position);
    }

    @Override
    public void updatePiecePositionBy(Position position) {
        this.position = position;
    }

    @Override
    public void checkObstacle(final Position futurePosition, final Map<Position, Piece> janggiBoard) {
        List<Position> moveRoute = makeRoute(futurePosition);
        for (Position position : moveRoute) {
            validateObstacle(janggiBoard, position);
        }
    }

    private void validateObstacle(final Map<Position, Piece> janggiBoard, final Position position) {
        if (janggiBoard.containsKey(position)) {
            throw new IllegalArgumentException("[ERROR] 상을 이동할 수 없습니다. 일보 전진 자리에 멱(장애물)이 존재합니다.");
        }
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        List<Position> route = new ArrayList<>();

        int dx = getBoardPosition().getRow() - position.getRow();
        int dy = getBoardPosition().getCol() - position.getCol();
        int presentRow = getBoardPosition().getRow();
        int presentCol = getBoardPosition().getCol();

        if (dx == 3 && dy == 2) {
            route.add(new Position(presentRow - 1, presentCol));
            route.add(new Position(presentRow - 2, presentCol - 1));
            route.add(new Position(presentRow - 3, presentCol - 2));
        }

        if (dx == 3 && dy == -2) {
            route.add(new Position(presentRow - 1, presentCol));
            route.add(new Position(presentRow - 2, presentCol + 1));
            route.add(new Position(presentRow - 3, presentCol + 2));
        }

        if (dx == 2 && dy == -3) {
            route.add(new Position(presentRow, presentCol + 1));
            route.add(new Position(presentRow - 1, presentCol + 2));
            route.add(new Position(presentRow - 2, presentCol + 3));
        }

        if (dx == -2 && dy == -3) {
            route.add(new Position(presentRow, presentCol + 1));
            route.add(new Position(presentRow + 1, presentCol + 2));
            route.add(new Position(presentRow + 2, presentCol + 3));
        }

        if (dx == -3 && dy == 2) {
            route.add(new Position(presentRow + 1, presentCol));
            route.add(new Position(presentRow + 2, presentCol - 1));
            route.add(new Position(presentRow + 3, presentCol - 2));
        }

        if (dx == -3 && dy == -2) {
            route.add(new Position(presentRow + 1, presentCol));
            route.add(new Position(presentRow + 2, presentCol + 1));
            route.add(new Position(presentRow + 3, presentCol + 2));
        }

        if (dx == 2 && dy == 3) {
            route.add(new Position(presentRow, presentCol - 1));
            route.add(new Position(presentRow - 1, presentCol - 2));
            route.add(new Position(presentRow - 2, presentCol - 3));
        }

        if (dx == -2 && dy == 3) {
            route.add(new Position(presentRow, presentCol - 1));
            route.add(new Position(presentRow + 1, presentCol - 2));
            route.add(new Position(presentRow + 2, presentCol - 3));
        }

        return route;
    }

    @Override
    public boolean isMove(final Position position) {
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
}
