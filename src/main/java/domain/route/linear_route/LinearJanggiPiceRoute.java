package domain.route.linear_route;

import static domain.route.Direction.DOWN;
import static domain.route.Direction.LEFT;
import static domain.route.Direction.RIGHT;
import static domain.route.Direction.UP;

import domain.JanggiPosition;
import domain.route.Direction;
import domain.pattern.Pattern;
import domain.route.JanggiPieceRoute;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LinearJanggiPiceRoute implements JanggiPieceRoute {

    protected Map<Direction, Pattern> directions;

    protected LinearJanggiPiceRoute(Map<Direction, Pattern> directions) {
        this.directions = directions;
    }

    @Override
    public List<Pattern> getRoute(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        if (afterPosition.file() == beforePosition.file()) {
            return setNewPathAndGetAdditionalSizeAboutLeftOrRight(beforePosition, afterPosition);
        }
        if (afterPosition.rank() == beforePosition.rank()) {
            return setNewPathAndGetAdditionalSizeAboutUpOrDown(beforePosition, afterPosition);
        }
        throw new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다.");
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutLeftOrRight(
            JanggiPosition beforePosition,
            JanggiPosition afterPosition
    ) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerYThan(beforePosition)) {
            newPath = RIGHT;
            additionalSize = afterPosition.getYGap(beforePosition);
        } else {
            newPath = LEFT;
            additionalSize = afterPosition.getYGap(beforePosition);
        }
        return createPattern(newPath, additionalSize);
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutUpOrDown(
            JanggiPosition beforePosition,
            JanggiPosition afterPosition
    ) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerXThan(beforePosition)) {
            newPath = DOWN;
            additionalSize = afterPosition.getXGap(beforePosition);
        } else {
            newPath = UP;
            additionalSize = afterPosition.getXGap(beforePosition);
        }
        return createPattern(newPath, additionalSize);
    }

    private List<Pattern> createPattern(Direction newPath, int additionalSize) {
        return Collections.nCopies(additionalSize, directions.get(newPath));
    }

    public Pattern getPattern(Direction direction) {
        return directions.get(direction);
    }

    @Override
    public List<Pattern> getPatterns(Direction direction) {
        return List.of(directions.get(direction));
    }
}
