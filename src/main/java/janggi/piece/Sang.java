package janggi.piece;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sang extends Piece {

    public Sang(final Team team, final Position position) {
        super(new PieceProfile(PieceType.SANG, team), position);
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
            throw new IllegalArgumentException("[ERROR] 상을 이동할 수 없습니다. 일보 전진 자리에 멱(장애물)이 존재합니다.");
        }
    }

    @Override
    public List<Position> makeRoute(final Position position) {
        final List<Position> route = new ArrayList<>();

        final int dx = getBoardPosition().getRow() - position.getRow();
        final int dy = getBoardPosition().getCol() - position.getCol();
        final int presentRow = getBoardPosition().getRow();
        final int presentCol = getBoardPosition().getCol();

        verticalRoute(dx, dy, route, presentRow, presentCol);
        horizontalRoute(dy, dx, route, presentRow, presentCol);

        return route;
    }

    private void verticalRoute(final int dx, final int dy, final List<Position> route, final int presentRow,
                               final int presentCol) {
        verticalUp(dx, dy, route, presentRow, presentCol);
        verticalDown(dx, dy, route, presentRow, presentCol);
    }

    private void verticalUp(final int dx, final int dy, final List<Position> route, final int presentRow,
                            final int presentCol) {
        if (dx == 3) {
            verticalLeftUpRoute(dy, route, presentRow, presentCol);
            verticalRightUpRoute(dy, route, presentRow, presentCol);
        }
    }

    private void verticalLeftUpRoute(final int dy, final List<Position> route, final int presentRow,
                                     final int presentCol) {
        if (dy == 2) {
            insertRoute(route, presentRow - 1, presentCol);
            insertRoute(route, presentRow - 2, presentCol - 1);
        }
    }

    private void verticalRightUpRoute(final int dy, final List<Position> route, final int presentRow,
                                      final int presentCol) {
        if (dy == -2) {
            insertRoute(route, presentRow - 1, presentCol);
            insertRoute(route, presentRow - 2, presentCol + 1);
        }
    }

    private void verticalDown(final int dx, final int dy, final List<Position> route, final int presentRow,
                              final int presentCol) {
        if (dx == -3) {
            verticalLeftDownRoute(dy, route, presentRow, presentCol);
            verticalRightDownRoute(dy, route, presentRow, presentCol);
        }
    }

    private void verticalRightDownRoute(final int dy, final List<Position> route, final int presentRow,
                                        final int presentCol) {
        if (dy == -2) {
            insertRoute(route, presentRow + 1, presentCol);
            insertRoute(route, presentRow + 2, presentCol + 1);
        }
    }

    private void verticalLeftDownRoute(final int dy, final List<Position> route, final int presentRow,
                                       final int presentCol) {
        if (dy == 2) {
            insertRoute(route, presentRow + 1, presentCol);
            insertRoute(route, presentRow + 2, presentCol - 1);
        }
    }

    private void horizontalRoute(final int dy, final int dx, final List<Position> route, final int presentRow,
                                 final int presentCol) {
        horizontalLeft(dy, dx, route, presentRow, presentCol);
        horizontalRight(dy, dx, route, presentRow, presentCol);
    }

    private void horizontalLeft(final int dy, final int dx, final List<Position> route, final int presentRow,
                                final int presentCol) {
        if (dy == 3) {
            horizontalLeftUpRout(dx, route, presentRow, presentCol);
            horizontalLeftDownRoute(dx, route, presentRow, presentCol);
        }
    }

    private void horizontalLeftUpRout(final int dx, final List<Position> route, final int presentRow,
                                      final int presentCol) {
        if (dx == 2) {
            insertRoute(route, presentRow, presentCol - 1);
            insertRoute(route, presentRow - 1, presentCol - 2);
        }
    }

    private void horizontalLeftDownRoute(final int dx, final List<Position> route, final int presentRow,
                                         final int presentCol) {
        if (dx == -2) {
            insertRoute(route, presentRow, presentCol - 1);
            insertRoute(route, presentRow + 1, presentCol - 2);
        }
    }

    private void horizontalRight(final int dy, final int dx, final List<Position> route, final int presentRow,
                                 final int presentCol) {
        if (dy == -3) {
            horizontalRightUpRoute(dx, route, presentRow, presentCol);
            horizontalRightDownRoute(dx, route, presentRow, presentCol);
        }
    }

    private void horizontalRightDownRoute(final int dx, final List<Position> route, final int presentRow,
                                          final int presentCol) {
        if (dx == -2) {
            insertRoute(route, presentRow, presentCol + 1);
            insertRoute(route, presentRow + 1, presentCol + 2);
        }
    }

    private void horizontalRightUpRoute(final int dx, final List<Position> route, final int presentRow,
                                        final int presentCol) {
        if (dx == 2) {
            insertRoute(route, presentRow, presentCol + 1);
            insertRoute(route, presentRow - 1, presentCol + 2);
        }
    }

    private void insertRoute(final List<Position> route, final int presentRow, final int presentCol) {
        route.add(new Position(presentRow, presentCol));
    }

    @Override
    public boolean isMove(final Position position) {
        final int dx = getBoardPosition().getRow() - position.getRow();
        final int dy = getBoardPosition().getCol() - position.getCol();

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
