package domain.piece.routestrategy;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;
import java.util.List;

public class SoldierMovingStrategy implements JanggiPieceRouteStrategy {

    @Override
    public List<MovingPattern> getRoute(final List<List<MovingPattern>> routes, final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        List<MovingPattern> patterns = routes.stream()
                .filter(route -> {
                    if (beforePosition.canMove(route)) {
                        JanggiPosition newPosition = beforePosition.move(route);
                        return newPosition.equals(afterPosition);
                    }
                    return false;
                })
                .findFirst()
                .orElseThrow(InvalidPathException::new);

        if (patterns.getFirst().isDiagonalPattern()) {
            if (!(beforePosition.isDiagonalMovablePalace() && afterPosition.isDiagonalMovablePalace())) {
                throw new IllegalStateException("해당 위치에서는 대각선으로 이동할 수 없습니다.");
            }
        }

        return patterns;
    }
}
