package domain.piece.routestrategy;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import janggiexception.PieceNotExistException;
import java.util.List;

public class NoneRouteStrategy implements JanggiPieceRouteStrategy {

    @Override
    public List<MovingPattern> getRoute(List<List<MovingPattern>> routes, JanggiPosition origin,
                                        JanggiPosition destination) {
        throw new PieceNotExistException();
    }
}
