package domain.piece.route.routestrategy;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import java.util.List;

public interface JanggiPieceRouteStrategy {

    List<MovingPattern> getRoute(JanggiSide side, List<List<MovingPattern>> routes, JanggiPosition origin, JanggiPosition destination);
}
