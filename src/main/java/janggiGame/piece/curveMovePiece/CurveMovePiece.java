package janggiGame.piece.curveMovePiece;

import janggiGame.Dot;
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
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        boolean isBlocked = routesWithPiece.values().stream().anyMatch(Objects::nonNull);
        if (isBlocked) {
            throw new UnsupportedOperationException("[ERROR] 경로에 말이 존재하면 이동할 수 없습니다.");
        }
    }

    @Override
    public List<Dot> getRoute(Dot origin, Dot destination) {
        int dx = origin.getDx(destination);
        int dy = origin.getDy(destination);
        validateRoute(dx, dy);
        return getRouteBySteps(origin, getMoveSteps(dx, dy));
    }

    private List<Dot> getRouteBySteps(Dot origin, List<Function<Dot, Dot>> moveSteps) {
        List<Dot> route = new ArrayList<>();
        for (Function<Dot, Dot> move : moveSteps) {
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

    protected Function<Dot, Dot> getFirstMove(int dx, int dy) {
        if (isFirstMoveVertical(dx, dy)) {
            return dy > 0 ? Dot::up : Dot::down;
        } else {
            return dx > 0 ? Dot::right : Dot::left;
        }
    }

    protected abstract boolean isFirstMoveVertical(int dx, int dy);

    protected abstract boolean isFirstMoveHorizontal(int dx, int dy);

    protected abstract List<Function<Dot, Dot>> getMoveSteps(int dx, int dy);
}
