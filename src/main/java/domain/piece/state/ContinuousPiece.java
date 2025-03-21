package domain.piece.state;

import static domain.pattern.Direction.DOWN;
import static domain.pattern.Direction.LEFT;
import static domain.pattern.Direction.RIGHT;
import static domain.pattern.Direction.UP;

import domain.JanggiPosition;
import domain.pattern.Direction;
import domain.pattern.Path;
import domain.pattern.Pattern;
import java.util.Collections;
import java.util.List;

public abstract class ContinuousPiece extends Moved {
    public List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        if (afterPosition.file() == beforePosition.file()) {
            return setNewPathAndGetAdditionalSizeAboutLeftOrRight(path, beforePosition, afterPosition);
        }
        if (afterPosition.rank() == beforePosition.rank()) {
            return setNewPathAndGetAdditionalSizeAboutUpOrDown(path, beforePosition, afterPosition);
        }
        throw new IllegalStateException("해당 경로로 이동할 수 없습니다.");
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutLeftOrRight(Path path, JanggiPosition beforePosition,
                                                                         JanggiPosition afterPosition) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerYThan(beforePosition)) {
            newPath = RIGHT;
            additionalSize = afterPosition.getYGap(beforePosition);
        } else {
            newPath = LEFT;
            additionalSize = afterPosition.getYGap(beforePosition);
        }
        return createPattern(path, newPath, additionalSize);
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutUpOrDown(Path path, JanggiPosition beforePosition,
                                                                      JanggiPosition afterPosition) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerXThan(beforePosition)) {
            newPath = DOWN;
            additionalSize = afterPosition.getXGap(beforePosition);
        } else {
            newPath = UP;
            additionalSize = afterPosition.getXGap(beforePosition);
        }
        return createPattern(path, newPath, additionalSize);
    }

    private List<Pattern> createPattern(Path path, Direction newPath, int additionalSize) {
        return Collections.nCopies(additionalSize, path.getPaths().get(newPath).getFirst());
    }

}
