package janggi.piece.multiplemovepiece;

import janggi.board.palace.Palace;
import janggi.piece.PalaceAwarePiece;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chariot extends PalaceAwarePiece {

    public Chariot(final Team team) {
        super(PieceType.CHARIOT, team);
    }

    @Override
    public void checkObstacle(final Position currentPosition, final Position targetPosition,
                              final Map<Position, Piece> janggiBoard) {
        final List<Position> makeRoute = makeRoute(currentPosition, targetPosition);
        for (final Position position : makeRoute) {
            validateObstacle(janggiBoard, position);
        }
    }

    private void validateObstacle(final Map<Position, Piece> janggiBoard, final Position position) {
        if (janggiBoard.containsKey(position)) {
            throw new IllegalArgumentException("[ERROR] 차를 이동할 수 없습니다. 차는 다른 기물을 넘어 다닐 수 없습니다.");
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
        diagonalRoute(dx, dy, route, currentRow, currentCol);

        return route;
    }

    private void verticalRoute(final int dx, final int dy, final List<Position> route, final int currentRow,
                               final int currentCol) {
        if (dx == 0) {
            verticalUpRoute(dy, route, currentRow, currentCol);
            verticalDownRoute(dy, route, currentRow, currentCol);
        }
    }

    private void verticalUpRoute(final int dy, final List<Position> route, final int currentRow,
                                 final int currentCol) {
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

    private void diagonalRoute(final int dx, final int dy, final List<Position> route, final int currentRow,
                               final int currentCol) {
        if (dx >= 1 && dy >= 1) {
            for (int i = 1; i < dy; i++) {
                insertRoute(route, currentRow - i, currentCol - i);
            }
        }

        if (dy <= -1 && dx <= -1) {
            for (int i = 1; i < Math.abs(dy); i++) {
                insertRoute(route, currentRow + i, currentCol + i);
            }
        }
    }

    private void insertRoute(final List<Position> route, final int currentRow, final int currentCol) {
        route.add(new Position(currentRow, currentCol));
    }

    @Override
    public void canMoveBy(final Position currentPosition, final Position targetPosition, final Palace palace) {
        if (palace.isInPalace(currentPosition, targetPosition)) {
            return;
        }

        if (isNotMove(currentPosition, targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 차가 움직일 수 없는 위치 입니다.");
        }
    }

    private boolean isNotMove(final Position currentPosition, final Position targetPosition) {
        return (currentPosition.row() != targetPosition.row())
                && (currentPosition.col() != targetPosition.col());
    }

}
