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
            if (afterPosition.y() > beforePosition.y()) {
                newPath = 포Path.RIGHT;
                additionalSize = afterPosition.y() - beforePosition.y();
            } else {
                newPath = 포Path.LEFT;
                additionalSize = beforePosition.y() - afterPosition.y();
            }
        }
        if (afterPosition.y() == beforePosition.y()) {
            if (beforePosition.y() > beforePosition.x()) {
                newPath = 포Path.DOWN;
                additionalSize = beforePosition.y() - beforePosition.x();
            } else {
                newPath = 포Path.UP;
                additionalSize = beforePosition.x() - beforePosition.y();
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
