package domain.piece.routestrategy;

import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.List;

public interface JanggiPieceRouteStrategy {

    List<Pattern> getRoute(List<List<Pattern>> routes, JanggiPosition origin, JanggiPosition destination);
}
