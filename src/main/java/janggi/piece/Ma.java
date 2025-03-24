package janggi.piece;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ma extends Piece {

    public Ma(final PieceProfile pieceProfile, final Position position) {
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
            throw new IllegalArgumentException("[ERROR] 마를 이동할 수 없습니다. 이동하려는 경로에 장애물이 존재합니다.");
        }
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

}
