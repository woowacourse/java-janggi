package domain.route.limited_route;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.Direction;
import java.util.List;
import java.util.Map;

public class EmptyRoute extends LimitedJanggiPieceRoute {

    public EmptyRoute() {
        super(Map.of());
    }

    @Override
    public List<Pattern> getRoute(final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        throw new IllegalStateException("움직일 말이 존재하지 않습니다.");
    }

    @Override
    public List<Pattern> getPatterns(final Direction direction) {
        throw new IllegalStateException("움직일 말이 존재하지 않습니다.");
    }
}
