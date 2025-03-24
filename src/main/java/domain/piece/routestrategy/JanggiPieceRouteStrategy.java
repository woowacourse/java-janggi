package domain.piece.routestrategy;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import java.util.List;

public interface JanggiPieceRouteStrategy {

    List<MovingPattern> getRoute(List<List<MovingPattern>> routes, JanggiPosition origin, JanggiPosition destination);
}
