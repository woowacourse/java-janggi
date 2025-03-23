package domain.piece.movingstrategy;

import domain.Direction;
import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.Map;

public interface JanggiPieceMovingStrategy {

    List<Pattern> getRoute(Map<Direction, List<Pattern>> routes, JanggiPosition origin, JanggiPosition destination);
}
