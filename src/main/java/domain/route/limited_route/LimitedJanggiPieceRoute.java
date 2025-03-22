package domain.route.limited_route;

import domain.JanggiPosition;
import domain.route.Direction;
import domain.pattern.Pattern;
import domain.route.JanggiPieceRoute;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LimitedJanggiPieceRoute implements JanggiPieceRoute {

    private final Map<Direction, List<Pattern>> routes;

    LimitedJanggiPieceRoute(final Map<Direction, List<Pattern>> routes) {
        this.routes = routes;
    }

    @Override
    public List<Pattern> getRoute(final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        return routes.entrySet().stream()
                .filter(entry -> {
                    List<Pattern> patterns = entry.getValue();
                    if (beforePosition.canMove(patterns)) {
                        JanggiPosition newPosition = beforePosition.move(patterns);
                        return newPosition.equals(afterPosition);
                    }
                    return false;
                })
                .findFirst()
                .map(Entry::getValue)
                .orElseThrow(() -> new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다."));
    }

    @Override
    public List<Pattern> getPatterns(final Direction direction) {
        return routes.get(direction);
    }
}
