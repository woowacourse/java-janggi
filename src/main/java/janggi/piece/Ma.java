package janggi.piece;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ma extends Piece {

    public Ma(final Team team, final Position position) {
        super(new PieceProfile(PieceType.MA, team), position);
    }

    @Override
    public void checkObstacle(final Position futurePosition, final Map<Position, Piece> janggiBoard) {
        final List<Position> moveRoute = makeRoute(futurePosition);
        for (final Position position : moveRoute) {
            validateObstacle(janggiBoard, position);
        }
    }

    private void validateObstacle(final Map<Position, Piece> janggiBoard, final Position position) {
        if (janggiBoard.containsKey(position)) {
            throw new IllegalArgumentException("[ERROR] 마를 이동할 수 없습니다. 일보 전진 자리에 멱(장애물)이 존재합니다.");
        }
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        final List<Position> route = new ArrayList<>();

        final int dx = getBoardPosition().getRow() - position.getRow();
        final int dy = getBoardPosition().getCol() - position.getCol();
        final int presentCol = getBoardPosition().getCol();
        final int presentRow = getBoardPosition().getRow();

        verticalRoute(dx, dy, route, presentRow, presentCol);
        horizontalRoute(dy, dx, route, presentRow, presentCol);

        return route;
    }

    private void verticalRoute(final int dx, final int dy, final List<Position> route, final int presentRow,
                               final int presentCol) {
        verticalUp(dx, route, presentRow, presentCol);
        verticalDown(dx, route, presentRow, presentCol);
    }

    private void verticalUp(final int dx, final List<Position> route, final int presentRow,
                            final int presentCol) {
        if (dx == 2) {
            insertRoute(route, presentRow - 1, presentCol);
        }
    }

    private void verticalDown(final int dx, final List<Position> route, final int presentRow,
                              final int presentCol) {
        if (dx == -2) {
            insertRoute(route, presentRow + 1, presentCol);
        }
    }

    private void horizontalRoute(final int dy, final int dx, final List<Position> route, final int presentRow,
                                 final int presentCol) {
        horizontalLeft(dy, route, presentRow, presentCol);
        horizontalRight(dy, route, presentRow, presentCol);

    }

    private void horizontalLeft(final int dy, final List<Position> route, final int presentRow,
                                final int presentCol) {
        if (dy == 2) {
            insertRoute(route, presentRow, presentCol - 1);
        }
    }

    private void horizontalRight(final int dy, final List<Position> route, final int presentRow,
                                 final int presentCol) {
        if (dy == -2) {
            insertRoute(route, presentRow, presentCol + 1);
        }
    }

    private void insertRoute(final List<Position> route, final int presentRow, final int presentCol) {
        route.add(new Position(presentRow, presentCol));
    }

    @Override
    public boolean isMove(final Position position) {
        final int dx = getBoardPosition().getRow() - position.getRow();
        final int dy = getBoardPosition().getCol() - position.getCol();

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
