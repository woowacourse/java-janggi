package janggi.piece;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Po extends Piece {

    public Po(final Team team, final Position position) {
        super(new PieceProfile(PieceType.PO, team), position);
    }

    @Override
    public void checkObstacle(final Position futurePosition, final Map<Position, Piece> janggiBoard) {
        final List<Position> route = makeRoute(futurePosition);
        validatePoMove(route, janggiBoard);
    }

    private void validatePoMove(final List<Position> moveRoute, final Map<Position, Piece> janggiBoard) {
        int obstacleCount = 0;

        for (final Position position : moveRoute) {
            final Piece piece = janggiBoard.get(position);
            if (janggiBoard.containsKey(position) && piece.isPo()) {
                throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다. 이동하려는 경로에 포가 존재합니다. 포는 포를 넘을 수 없습니다.");
            }

            if (janggiBoard.containsKey(position)) {
                obstacleCount++;
            }
        }
        validateObstacleBy(obstacleCount);
    }

    private void validateObstacleBy(final int obstacle) {
        if (obstacle == 0) {
            throw new IllegalArgumentException("[ERROR] 포를 이동할 수 없습니다. 포는 반드시 포를 제외한 기물 하나를 넘어야 합니다.");
        }

        if (obstacle >= 2) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다. 이동하려는 경로에 " + obstacle + "개의 장애물이 존재합니다.");
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
        if (dx == 0) {
            verticalUpRoute(dy, route, presentRow, presentCol);
            verticalDownRoute(dy, route, presentRow, presentCol);
        }
    }

    private void verticalUpRoute(final int dy, final List<Position> route, final int presentRow, final int presentCol) {
        if (dy > 0) {
            for (int i = 1; i < dy; i++) {
                insertRoute(route, presentRow, presentCol - i);
            }
        }
    }

    private void verticalDownRoute(final int dy, final List<Position> route, final int presentRow,
                                   final int presentCol) {
        if (dy < 0) {
            for (int i = 1; i < Math.abs(dy); i++) {
                insertRoute(route, presentRow, presentCol + i);
            }
        }
    }

    private void horizontalRoute(final int dy, final int dx, final List<Position> route, final int presentRow,
                                 final int presentCol) {
        if (dy == 0) {
            horizontalRightRoute(dx, route, presentRow, presentCol);
            horizontalLeftRoute(dx, route, presentRow, presentCol);
        }
    }

    private void horizontalRightRoute(final int dx, final List<Position> route, final int presentRow,
                                      final int presentCol) {
        if (dx > 0) {
            for (int i = 1; i < dx; i++) {
                insertRoute(route, presentRow - i, presentCol);
            }
        }
    }

    private void horizontalLeftRoute(final int dx, final List<Position> route, final int presentRow,
                                     final int presentCol) {
        if (dx < 0) {
            for (int i = 1; i < Math.abs(dx); i++) {
                insertRoute(route, presentRow + i, presentCol);
            }
        }
    }

    private void insertRoute(final List<Position> route, final int presentRow, final int presentCol) {
        route.add(new Position(presentRow, presentCol));
    }

    @Override
    public boolean isMove(final Position position) {
        if (super.getBoardPosition().getRow() == position.getRow()
                || super.getBoardPosition().getCol() == position.getCol()) {
            return true;
        }
        throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
    }

    @Override
    protected boolean isPo() {
        return true;
    }
}
