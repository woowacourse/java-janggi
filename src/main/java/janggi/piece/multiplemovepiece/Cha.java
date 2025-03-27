package janggi.piece.multiplemovepiece;

import janggi.piece.Piece;
import janggi.piece.PieceProfile;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cha extends Piece {

    public Cha(final Team team, final Position position) {
        super(new PieceProfile(PieceType.CHA, team), position);
    }

    @Override
    public void checkObstacle(final Position futurePosition, final Map<Position, Piece> janggiBoard) {
        final List<Position> makeRoute = makeRoute(futurePosition);
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

    private void verticalUpRoute(final int dy, final List<Position> route, final int presentRow,
                                 final int presentCol) {
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
        if ((getBoardPosition().getRow() == position.getRow()) || (getBoardPosition().getCol() == position.getCol())) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 차가 움직일 수 없는 위치 입니다.");
    }

}
