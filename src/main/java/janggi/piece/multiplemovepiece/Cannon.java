package janggi.piece.multiplemovepiece;

import janggi.piece.Piece;
import janggi.piece.PieceProfile;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends Piece {

    public Cannon(final Team team) {
        super(new PieceProfile(PieceType.CANNON, team));
    }

    @Override
    public void checkObstacle(final Position presentPosition, final Position futurePosition,
                              final Map<Position, Piece> janggiBoard) {
        final List<Position> route = makeRoute(presentPosition, futurePosition);
        validatePoMove(route, janggiBoard);
    }

    private void validatePoMove(final List<Position> moveRoute, final Map<Position, Piece> janggiBoard) {
        int obstacleCount = 0;

        for (final Position position : moveRoute) {
            final Piece piece = janggiBoard.get(position);
            if (janggiBoard.containsKey(position) && PieceType.isCannon(piece.getPieceProfile().getPieceType())) {
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
    public List<Position> makeRoute(final Position presentPosition, final Position position) {
        final List<Position> route = new ArrayList<>();
        final int dx = presentPosition.row() - position.row();
        final int dy = presentPosition.col() - position.col();
        final int presentCol = presentPosition.col();
        final int presentRow = presentPosition.row();

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
    public void canMoveBy(final Position presentPosition, final Position position) {
        if (isNotMove(presentPosition, position)) {
            throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
        }
    }

    private boolean isNotMove(final Position presentPosition, final Position position) {
        return presentPosition.row() != position.row()
                && presentPosition.col() != position.col();
    }
}
