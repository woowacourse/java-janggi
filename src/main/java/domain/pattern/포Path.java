package domain.pattern;

import domain.Position;
import java.util.Collections;
import java.util.List;

public enum 포Path {
    RIGHT(List.of(Pattern.RIGHT)),
    DOWN(List.of(Pattern.DOWN)),
    LEFT(List.of(Pattern.LEFT)),
    UP(List.of(Pattern.UP));

    private List<Pattern> patterns;

    포Path(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    public static List<Pattern> getPath(Position beforePosition, Position afterPosition) {
        포Path newPath = null;
        int additionalSize = 0;
        if (afterPosition.x() == beforePosition.x()) {
            if (afterPosition.isBiggerYThan(beforePosition)) {
                newPath = 포Path.RIGHT;
                additionalSize = afterPosition.getYGap(beforePosition);
            } else {
                newPath = 포Path.LEFT;
                additionalSize = afterPosition.getYGap(beforePosition);
            }
        }
        if (afterPosition.y() == beforePosition.y()) {
            if (afterPosition.isBiggerXThan(beforePosition)) {
                newPath = 포Path.DOWN;
                additionalSize = afterPosition.getXGap(beforePosition);
            } else {
                newPath = 포Path.UP;
                additionalSize = afterPosition.getXGap(beforePosition);
            }
        }
        if (newPath == null) {
            throw new IllegalStateException("포는 해당 경로로 이동할 수 없습니다.");
        }
        return create포Pattern(newPath, additionalSize);
    }

    private static List<Pattern> create포Pattern(포Path newPath, int additionalSize) {
        return Collections.nCopies(additionalSize, newPath.getPattern());
    }

    public Pattern getPattern() {
        return this.patterns.get(0);
    }
}
