package domain.piece.routestrategy;

import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.List;

public class LimitedRouteStrategy implements JanggiPieceRouteStrategy {

    @Override
    public List<Pattern> getRoute(final List<List<Pattern>> routes, final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        return routes.stream()
                .filter(route -> {
                    if (beforePosition.canMove(route)) {
                        JanggiPosition newPosition = beforePosition.move(route);
                        return newPosition.equals(afterPosition);
                    }
                    return false;
                })
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다."));
    }
}
