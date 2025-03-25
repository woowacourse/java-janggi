package domain.piece.route.routestrategy;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;
import java.util.List;

public class PalaceRouteStrategy implements JanggiPieceRouteStrategy {

    @Override
    public List<MovingPattern> getRoute(final JanggiSide side, final List<List<MovingPattern>> routes, final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        if (!afterPosition.isPalace()) {
            throw new IllegalStateException("해당 기물은 궁성 밖을 벗어날 수 없습니다.");
        }
        return routes.stream()
                .filter(route -> {
                    if (beforePosition.canMove(route)) {
                        JanggiPosition newPosition = beforePosition.move(route);
                        return newPosition.equals(afterPosition);
                    }
                    return false;
                })
                .findFirst()
                .orElseThrow(InvalidPathException::new);
    }
}
