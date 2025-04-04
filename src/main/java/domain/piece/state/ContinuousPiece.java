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
    @Override
    public List<Pattern> findMovablePath(Path path, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        if (afterPosition.isSameFileWith(beforePosition)) {
            return setNewPathAndGetAdditionalSizeAboutLeftOrRight(path, beforePosition, afterPosition);
        }
        if (afterPosition.isSameRankWith(beforePosition)) {
            return setNewPathAndGetAdditionalSizeAboutUpOrDown(path, beforePosition, afterPosition);
        }
        throw new IllegalArgumentException("해당 경로로 이동할 수 없습니다.");
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutLeftOrRight(Path path, JanggiPosition beforePosition,
                                                                         JanggiPosition afterPosition) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerRankThan(beforePosition)) {
            newPath = RIGHT;
            additionalSize = afterPosition.getRankGap(beforePosition);
        } else {
            newPath = LEFT;
            additionalSize = afterPosition.getRankGap(beforePosition);
        }
        return createPattern(path, newPath, additionalSize);
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutUpOrDown(Path path, JanggiPosition beforePosition,
                                                                      JanggiPosition afterPosition) {
        Direction newPath;
        int additionalSize;
        if (afterPosition.isBiggerFileThan(beforePosition)) {
            newPath = DOWN;
            additionalSize = afterPosition.getFileGap(beforePosition);
        } else {
            newPath = UP;
            additionalSize = afterPosition.getFileGap(beforePosition);
        }
        return createPattern(path, newPath, additionalSize);
    }

    private List<Pattern> createPattern(Path path, Direction newPath, int additionalSize) {
        return Collections.nCopies(
                additionalSize, path
                        .getPaths()
                        .get(newPath)
                        .getFirst()
        );
    }

}
