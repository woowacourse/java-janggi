package janggi.piece.multiplemovepiece;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends Piece {

    public Cannon(final Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    public void checkObstacle(final Position currentPosition, final Position targetPosition,
                              final Map<Position, Piece> janggiBoard) {
        final List<Position> route = makeRoute(currentPosition, targetPosition);
        validatePoMove(route, janggiBoard);
    }

    private void validatePoMove(final List<Position> moveRoute, final Map<Position, Piece> janggiBoard) {
        int obstacleCount = 0;

        for (final Position position : moveRoute) {
            final Piece piece = janggiBoard.get(position);
            if (janggiBoard.containsKey(position) && PieceType.isCannon(piece.getPieceType())) {
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
    public List<Position> makeRoute(final Position currentPosition, final Position targetPosition) {
        final List<Position> route = new ArrayList<>();
        final int dx = currentPosition.row() - targetPosition.row();
        final int dy = currentPosition.col() - targetPosition.col();
        final int currentCol = currentPosition.col();
        final int currentRow = currentPosition.row();

        verticalRoute(dx, dy, route, currentRow, currentCol);
        horizontalRoute(dy, dx, route, currentRow, currentCol);
        return route;
    }

    private void verticalRoute(final int dx, final int dy, final List<Position> route, final int currentRow,
                               final int currentCol) {
        if (dx == 0) {
            verticalUpRoute(dy, route, currentRow, currentCol);
            verticalDownRoute(dy, route, currentRow, currentCol);
        }
    }

    private void verticalUpRoute(final int dy, final List<Position> route, final int currentRow, final int currentCol) {
        if (dy > 0) {
            for (int i = 1; i < dy; i++) {
                insertRoute(route, currentRow, currentCol - i);
            }
        }
    }

    private void verticalDownRoute(final int dy, final List<Position> route, final int currentRow,
                                   final int currentCol) {
        if (dy < 0) {
            for (int i = 1; i < Math.abs(dy); i++) {
                insertRoute(route, currentRow, currentCol + i);
            }
        }
    }

    private void horizontalRoute(final int dy, final int dx, final List<Position> route, final int currentRow,
                                 final int currentCol) {
        if (dy == 0) {
            horizontalRightRoute(dx, route, currentRow, currentCol);
            horizontalLeftRoute(dx, route, currentRow, currentCol);
        }
    }

    private void horizontalRightRoute(final int dx, final List<Position> route, final int currentRow,
                                      final int currentCol) {
        if (dx > 0) {
            for (int i = 1; i < dx; i++) {
                insertRoute(route, currentRow - i, currentCol);
            }
        }
    }

    private void horizontalLeftRoute(final int dx, final List<Position> route, final int currentRow,
                                     final int currentCol) {
        if (dx < 0) {
            for (int i = 1; i < Math.abs(dx); i++) {
                insertRoute(route, currentRow + i, currentCol);
            }
        }
    }

    private void insertRoute(final List<Position> route, final int currentRow, final int currentCol) {
        route.add(new Position(currentRow, currentCol));
    }

    @Override
    public void canMoveBy(final Position currentPosition, final Position targetPosition) {
        if (isNotMove(currentPosition, targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
        }
    }

    private boolean isNotMove(final Position currentPosition, final Position targetPosition) {
        return currentPosition.row() != targetPosition.row()
                && currentPosition.col() != targetPosition.col();
    }
}
