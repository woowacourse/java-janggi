package janggi.piece.multiplemovepiece;

import janggi.piece.PalaceIgnorantPiece;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends PalaceIgnorantPiece {

    public Elephant(final Team team) {
        super(PieceType.ELEPHANT, team);
    }

    @Override
    public void checkObstacle(final Position currentPosition, final Position targetPosition,
                              final Map<Position, Piece> janggiBoard) {
        final List<Position> moveRoute = makeRoute(currentPosition, targetPosition);
        for (final Position position : moveRoute) {
            validateObstacle(janggiBoard, position);
        }
    }

    private void validateObstacle(final Map<Position, Piece> janggiBoard, final Position position) {
        if (janggiBoard.containsKey(position)) {
            throw new IllegalArgumentException("[ERROR] 상을 이동할 수 없습니다. 일보 전진 자리에 멱(장애물)이 존재합니다.");
        }
    }

    @Override
    public List<Position> makeRoute(final Position currentPosition, final Position targetPosition) {
        final List<Position> route = new ArrayList<>();

        final int dx = currentPosition.row() - targetPosition.row();
        final int dy = currentPosition.col() - targetPosition.col();
        final int currentRow = currentPosition.row();
        final int currentCol = currentPosition.col();

        verticalRoute(dx, dy, route, currentRow, currentCol);
        horizontalRoute(dy, dx, route, currentRow, currentCol);

        return route;
    }

    private void verticalRoute(final int dx, final int dy, final List<Position> route, final int currentRow,
                               final int currentCol) {
        verticalUp(dx, dy, route, currentRow, currentCol);
        verticalDown(dx, dy, route, currentRow, currentCol);
    }

    private void verticalUp(final int dx, final int dy, final List<Position> route, final int currentRow,
                            final int currentCol) {
        if (dx == 3) {
            verticalLeftUpRoute(dy, route, currentRow, currentCol);
            verticalRightUpRoute(dy, route, currentRow, currentCol);
        }
    }

    private void verticalLeftUpRoute(final int dy, final List<Position> route, final int currentRow,
                                     final int currentCol) {
        if (dy == 2) {
            insertRoute(route, currentRow - 1, currentCol);
            insertRoute(route, currentRow - 2, currentCol - 1);
        }
    }

    private void verticalRightUpRoute(final int dy, final List<Position> route, final int currentRow,
                                      final int currentCol) {
        if (dy == -2) {
            insertRoute(route, currentRow - 1, currentCol);
            insertRoute(route, currentRow - 2, currentCol + 1);
        }
    }

    private void verticalDown(final int dx, final int dy, final List<Position> route, final int currentRow,
                              final int currentCol) {
        if (dx == -3) {
            verticalLeftDownRoute(dy, route, currentRow, currentCol);
            verticalRightDownRoute(dy, route, currentRow, currentCol);
        }
    }

    private void verticalRightDownRoute(final int dy, final List<Position> route, final int currentRow,
                                        final int currentCol) {
        if (dy == -2) {
            insertRoute(route, currentRow + 1, currentCol);
            insertRoute(route, currentRow + 2, currentCol + 1);
        }
    }

    private void verticalLeftDownRoute(final int dy, final List<Position> route, final int currentRow,
                                       final int currentCol) {
        if (dy == 2) {
            insertRoute(route, currentRow + 1, currentCol);
            insertRoute(route, currentRow + 2, currentCol - 1);
        }
    }

    private void horizontalRoute(final int dy, final int dx, final List<Position> route, final int currentRow,
                                 final int currentCol) {
        horizontalLeft(dy, dx, route, currentRow, currentCol);
        horizontalRight(dy, dx, route, currentRow, currentCol);
    }

    private void horizontalLeft(final int dy, final int dx, final List<Position> route, final int currentRow,
                                final int currentCol) {
        if (dy == 3) {
            horizontalLeftUpRout(dx, route, currentRow, currentCol);
            horizontalLeftDownRoute(dx, route, currentRow, currentCol);
        }
    }

    private void horizontalLeftUpRout(final int dx, final List<Position> route, final int currentRow,
                                      final int currentCol) {
        if (dx == 2) {
            insertRoute(route, currentRow, currentCol - 1);
            insertRoute(route, currentRow - 1, currentCol - 2);
        }
    }

    private void horizontalLeftDownRoute(final int dx, final List<Position> route, final int currentRow,
                                         final int currentCol) {
        if (dx == -2) {
            insertRoute(route, currentRow, currentCol - 1);
            insertRoute(route, currentRow + 1, currentCol - 2);
        }
    }

    private void horizontalRight(final int dy, final int dx, final List<Position> route, final int currentRow,
                                 final int currentCol) {
        if (dy == -3) {
            horizontalRightUpRoute(dx, route, currentRow, currentCol);
            horizontalRightDownRoute(dx, route, currentRow, currentCol);
        }
    }

    private void horizontalRightDownRoute(final int dx, final List<Position> route, final int currentRow,
                                          final int currentCol) {
        if (dx == -2) {
            insertRoute(route, currentRow, currentCol + 1);
            insertRoute(route, currentRow + 1, currentCol + 2);
        }
    }

    private void horizontalRightUpRoute(final int dx, final List<Position> route, final int currentRow,
                                        final int currentCol) {
        if (dx == 2) {
            insertRoute(route, currentRow, currentCol + 1);
            insertRoute(route, currentRow - 1, currentCol + 2);
        }
    }

    private void insertRoute(final List<Position> route, final int currentRow, final int currentCol) {
        route.add(new Position(currentRow, currentCol));
    }

    @Override
    public void canMoveBy(final Position currentPosition, final Position targetPosition) {
        final int dx = targetPosition.calculateDifferenceRow(currentPosition.row());
        final int dy = targetPosition.calculateDifferenceCol(currentPosition.col());

        if (isNotMove(dx, dy)) {
            throw new IllegalArgumentException("[ERROR] 상이 움직일 수 없는 위치입니다.");
        }
    }

    private boolean isNotMove(final int dx, final int dy) {
        return !(dx == 2 && dy == 3 || dy == 2 && dx == 3);
    }

}
