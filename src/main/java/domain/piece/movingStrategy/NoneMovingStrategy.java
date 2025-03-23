package domain.piece.movingStrategy;

import domain.Direction;
import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.Map;

public class NoneMovingStrategy implements JanggiPieceMovingStrategy {


    @Override
    public List<Pattern> getRoute(Map<Direction, List<Pattern>> routes, JanggiPosition origin,
                                  JanggiPosition destination) {
        throw new IllegalStateException("움직일 기물이 존재하지 않습니다.");
    }
}
