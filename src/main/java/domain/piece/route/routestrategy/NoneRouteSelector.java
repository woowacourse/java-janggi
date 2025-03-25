package domain.piece.route.routestrategy;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import janggiexception.PieceNotExistException;
import java.util.List;

public class NoneRouteSelector implements JanggiPieceRouteSelector {

    @Override
    public List<MovingPattern> getRoute(final JanggiSide side, List<List<MovingPattern>> routes, JanggiPosition origin,
                                        JanggiPosition destination) {
        throw new PieceNotExistException();
    }
}
