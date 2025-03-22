package domain.route;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import java.util.List;

public interface JanggiPieceRoute {

    List<Pattern> getRoute(final JanggiPosition beforePosition, final JanggiPosition afterPosition);
    List<Pattern> getPatterns(final Direction direction);
}
