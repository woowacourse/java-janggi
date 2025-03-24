package domain.piece.routestrategy;

import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.List;

public class NoneRouteStrategy implements JanggiPieceRouteStrategy {

    @Override
    public List<Pattern> getRoute(List<List<Pattern>> routes, JanggiPosition origin,
                                  JanggiPosition destination) {
        throw new IllegalStateException("움직일 기물이 존재하지 않습니다.");
    }
}
