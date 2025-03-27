package janggi.piece.multiplemovepiece;

import janggi.piece.Piece;
import janggi.piece.PieceProfile;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    public Horse(final Team team) {
        super(new PieceProfile(PieceType.HORSE, team));
    }

    @Override
    public void checkObstacle(final Position presentPosition, final Position futurePosition,
                              final Map<Position, Piece> janggiBoard) {
        final List<Position> moveRoute = makeRoute(presentPosition, futurePosition);
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
    public List<Position> makeRoute(final Position presentPosition, final Position position) {
        final List<Position> route = new ArrayList<>();

        final int dx = presentPosition.row() - position.row();
        final int dy = presentPosition.col() - position.col();
        final int presentCol = presentPosition.col();
        final int presentRow = presentPosition.row();

        verticalRoute(dx, route, presentRow, presentCol);
        horizontalRoute(dy, route, presentRow, presentCol);

        return route;
    }

    private void verticalRoute(final int dx, final List<Position> route, final int presentRow,
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

    private void horizontalRoute(final int dy, final List<Position> route, final int presentRow,
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
    public void canMoveBy(final Position presentPosition, final Position position) {
        final int dx = position.calculateDifferenceRow(presentPosition.row());
        final int dy = position.calculateDifferenceCol(presentPosition.col());

        if (isNotMove(dy, dx)) {
            throw new IllegalArgumentException("[ERROR] 마가 움직일 수 없는 위치입니다.");
        }
    }

    private boolean isNotMove(final int dy, final int dx) {
        return !(dy == 1 && dx == 2 || dx == 1 && dy == 2);
    }

}
