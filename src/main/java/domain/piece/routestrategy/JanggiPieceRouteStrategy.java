package domain.piece.routestrategy;

import domain.Direction;
import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.Map;

public interface JanggiPieceRouteStrategy {

    List<Pattern> getRoute(List<List<Pattern>> routes, JanggiPosition origin, JanggiPosition destination);
}
