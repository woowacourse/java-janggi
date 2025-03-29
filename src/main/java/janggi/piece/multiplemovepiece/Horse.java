package janggi.piece.multiplemovepiece;

import janggi.piece.PalaceIgnorantPiece;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Horse extends PalaceIgnorantPiece {

    public Horse(final Team team) {
        super(PieceType.HORSE, team);
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
            throw new IllegalArgumentException("[ERROR] 마를 이동할 수 없습니다. 일보 전진 자리에 멱(장애물)이 존재합니다.");
        }
    }

    @Override
    public List<Position> makeRoute(final Position currentPosition, final Position targetPosition) {
        final List<Position> route = new ArrayList<>();

        final int dx = currentPosition.row() - targetPosition.row();
        final int dy = currentPosition.col() - targetPosition.col();
        final int currentCol = currentPosition.col();
        final int currentRow = currentPosition.row();

        verticalRoute(dx, route, currentRow, currentCol);
        horizontalRoute(dy, route, currentRow, currentCol);

        return route;
    }

    private void verticalRoute(final int dx, final List<Position> route, final int currentRow,
                               final int currentCol) {
        verticalUp(dx, route, currentRow, currentCol);
        verticalDown(dx, route, currentRow, currentCol);
    }

    private void verticalUp(final int dx, final List<Position> route, final int currentRow,
                            final int currentCol) {
        if (dx == 2) {
            insertRoute(route, currentRow - 1, currentCol);
        }
    }

    private void verticalDown(final int dx, final List<Position> route, final int currentRow,
                              final int currentCol) {
        if (dx == -2) {
            insertRoute(route, currentRow + 1, currentCol);
        }
    }

    private void horizontalRoute(final int dy, final List<Position> route, final int currentRow,
                                 final int currentCol) {
        horizontalLeft(dy, route, currentRow, currentCol);
        horizontalRight(dy, route, currentRow, currentCol);
    }

    private void horizontalLeft(final int dy, final List<Position> route, final int currentRow,
                                final int currentCol) {
        if (dy == 2) {
            insertRoute(route, currentRow, currentCol - 1);
        }
    }

    private void horizontalRight(final int dy, final List<Position> route, final int currentRow,
                                 final int currentCol) {
        if (dy == -2) {
            insertRoute(route, currentRow, currentCol + 1);
        }
    }

    private void insertRoute(final List<Position> route, final int currentRow, final int currentCol) {
        route.add(new Position(currentRow, currentCol));
    }

    @Override
    public void canMoveBy(final Position currentPosition, final Position targetPosition) {
        final int dx = targetPosition.calculateDifferenceRow(currentPosition.row());
        final int dy = targetPosition.calculateDifferenceCol(currentPosition.col());

        if (isNotMove(dy, dx)) {
            throw new IllegalArgumentException("[ERROR] 마가 움직일 수 없는 위치입니다.");
        }
    }

    private boolean isNotMove(final int dy, final int dx) {
        return !(dy == 1 && dx == 2 || dx == 1 && dy == 2);
    }

}
