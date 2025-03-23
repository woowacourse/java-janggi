package domain.piece.movingstrategy;

import static domain.Direction.DOWN;
import static domain.Direction.LEFT;
import static domain.Direction.RIGHT;
import static domain.Direction.UP;

import domain.Direction;
import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LinearMovingStrategy implements JanggiPieceMovingStrategy {

    @Override
    public List<Pattern> getRoute(final Map<Direction, List<Pattern>> routes, final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        if (afterPosition.rank() == beforePosition.rank()) {
            return setNewPathAndGetAdditionalSizeAboutLeftOrRight(routes, beforePosition, afterPosition);
        }
        if (afterPosition.file() == beforePosition.file()) {
            return setNewPathAndGetAdditionalSizeAboutUpOrDown(routes, beforePosition, afterPosition);
        }
        throw new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다.");
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutLeftOrRight(
            final Map<Direction, List<Pattern>> routes,
            final JanggiPosition beforePosition,
            final JanggiPosition afterPosition
    ) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerFileThan(beforePosition)) {
            newPath = RIGHT;
            additionalSize = afterPosition.getFileGap(beforePosition);
        } else {
            newPath = LEFT;
            additionalSize = afterPosition.getFileGap(beforePosition);
        }
        return createPattern(routes.get(newPath), additionalSize);
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutUpOrDown(
            final Map<Direction, List<Pattern>> routes,
            final JanggiPosition beforePosition,
            final JanggiPosition afterPosition
    ) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerRankThan(beforePosition)) {
            newPath = DOWN;
            additionalSize = afterPosition.getRankGap(beforePosition);
        } else {
            newPath = UP;
            additionalSize = afterPosition.getRankGap(beforePosition);
        }
        return createPattern(routes.get(newPath), additionalSize);
    }

    private List<Pattern> createPattern(final List<Pattern> direction, int additionalSize) {
        return Collections.nCopies(additionalSize, direction.getFirst());
    }
}
