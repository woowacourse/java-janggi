package domain.piece.movingstrategy;

import domain.Direction;
import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LimitedMovingStrategy implements JanggiPieceMovingStrategy {

    @Override
    public List<Pattern> getRoute(final Map<Direction, List<Pattern>> routes, final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        return routes.entrySet().stream()
                .filter(entry -> {
                    List<Pattern> patterns = entry.getValue();
                    if (beforePosition.canMove(patterns)) {
                        JanggiPosition newPosition = beforePosition.move(patterns);
                        return newPosition.equals(afterPosition);
                    }
                    return false;
                })
                .findFirst()
                .map(Entry::getValue)
                .orElseThrow(() -> new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다."));
    }
}
