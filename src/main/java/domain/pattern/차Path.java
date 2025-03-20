package domain.pattern;

import static domain.pattern.Direction.DOWN;
import static domain.pattern.Direction.LEFT;
import static domain.pattern.Direction.RIGHT;
import static domain.pattern.Direction.UP;

import domain.JanggiPosition;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class 차Path extends Path {
    public 차Path() {
        super(List.of(RIGHT, DOWN, LEFT, UP), Map.of(
                RIGHT, List.of(Pattern.RIGHT),
                DOWN, List.of(Pattern.DOWN),
                LEFT, List.of(Pattern.LEFT),
                UP, List.of(Pattern.UP)
        ));
    }

    @Override
    public List<Pattern> getPath(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        if (afterPosition.file() == beforePosition.file()) {
            return setNewPathAndGetAdditionalSizeAboutLeftOrRight(beforePosition, afterPosition);
        }
        if (afterPosition.rank() == beforePosition.rank()) {
            return setNewPathAndGetAdditionalSizeAboutUpOrDown(beforePosition, afterPosition);
        }
        throw new IllegalStateException("차는 해당 경로로 이동할 수 없습니다.");
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutLeftOrRight(JanggiPosition beforePosition,
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
        return create차Pattern(newPath, additionalSize);
    }

    private List<Pattern> setNewPathAndGetAdditionalSizeAboutUpOrDown(JanggiPosition beforePosition,
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
        return create차Pattern(newPath, additionalSize);
    }

    private List<Pattern> create차Pattern(Direction newPath, int additionalSize) {
        return Collections.nCopies(additionalSize, paths.get(newPath).getFirst());
    }
}
