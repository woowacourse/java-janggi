package domain.route;

import domain.JanggiPosition;
import domain.pattern.Direction;
import domain.pattern.Pattern;
import java.util.List;

public interface JanggiPieceRoute {

    List<Pattern> getRoute(JanggiPosition beforePosition, JanggiPosition afterPosition);
    List<Pattern> getPatterns(Direction direction);
}
