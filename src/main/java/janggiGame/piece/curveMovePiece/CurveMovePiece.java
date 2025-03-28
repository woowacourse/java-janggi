package janggiGame.piece.curveMovePiece;

import janggiGame.position.Position;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class CurveMovePiece extends Piece {

    public CurveMovePiece(Dynasty dynasty, Type type) {
        super(dynasty, type);
    }

    @Override
    public void validateMove(Map<Position, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        boolean isBlocked = routesWithPiece.values().stream().anyMatch(Objects::nonNull);
        if (isBlocked) {
            throw new UnsupportedOperationException("[ERROR] 경로에 말이 존재하면 이동할 수 없습니다.");
        }
    }

    @Override
    public List<Position> getRoute(Position origin, Position destination) {
        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);
        validateRoute(dx, dy);
        return getRouteBySteps(origin, getMoveSteps(dx, dy));
    }

    private List<Position> getRouteBySteps(Position origin, List<Function<Position, Position>> moveSteps) {
        List<Position> route = new ArrayList<>();
        for (Function<Position, Position> move : moveSteps) {
            origin = move.apply(origin);
            route.add(origin);
        }
        return route;
    }

    @Override
    protected void validateRoute(int dx, int dy) {
        if (!(isFirstMoveVertical(dx, dy) || isFirstMoveHorizontal(dx, dy))) {
            throw new UnsupportedOperationException("[ERROR] 이동할 수 있는 목적지가 아닙니다.");
        }
    }

    protected Function<Position, Position> getFirstMove(int dx, int dy) {
        if (isFirstMoveVertical(dx, dy)) {
            return dy > 0 ? Position::up : Position::down;
        } else {
            return dx > 0 ? Position::right : Position::left;
        }
    }

    protected abstract boolean isFirstMoveVertical(int dx, int dy);

    protected abstract boolean isFirstMoveHorizontal(int dx, int dy);

    protected abstract List<Function<Position, Position>> getMoveSteps(int dx, int dy);
}
