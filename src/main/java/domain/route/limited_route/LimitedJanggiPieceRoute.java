package domain.route.limited_route;

import domain.JanggiPosition;
import domain.route.Direction;
import domain.pattern.Pattern;
import domain.route.JanggiPieceRoute;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LimitedJanggiPieceRoute implements JanggiPieceRoute {

    protected Map<Direction, List<Pattern>> routes;

    protected LimitedJanggiPieceRoute(Map<Direction, List<Pattern>> routes) {
        this.routes = routes;
    }

    @Override
    public List<Pattern> getRoute(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        return routes.entrySet().stream()
                .filter(entry -> {
                    List<Pattern> patterns = entry.getValue();
                    JanggiPosition newPosition = beforePosition.move(patterns);

                    return newPosition.equals(afterPosition);
                })
                .findFirst()
                .map(Entry::getValue)
                .orElseThrow(() -> new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다."));
    }

    @Override
    public List<Pattern> getPatterns(Direction direction) {
        return routes.get(direction);
    }
}
