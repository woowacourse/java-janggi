package domain.pattern;

import java.util.Collections;
import java.util.List;

public enum 차Path {
    RIGHT(List.of(Pattern.RIGHT)),
    DOWN(List.of(Pattern.DOWN)),
    LEFT(List.of(Pattern.LEFT)),
    UP(List.of(Pattern.UP));

    private List<Pattern> patterns;

    차Path(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    public static List<Pattern> getPath(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        차Path newPath = null;
        int additionalSize = 0;
        if (afterRow == beforeRow) {
            if (afterColumn > beforeColumn) {
                newPath = 차Path.RIGHT;
                additionalSize = afterColumn - beforeColumn;
            } else {
                newPath = 차Path.LEFT;
                additionalSize = beforeColumn - afterColumn;
            }
        }
        if (afterColumn == beforeColumn) {
            if (afterRow > beforeRow) {
                newPath = 차Path.DOWN;
                additionalSize = afterRow - beforeRow;
            } else {
                newPath = 차Path.UP;
                additionalSize = beforeRow - afterRow;
            }
        }
        if (newPath == null) {
            throw new IllegalStateException("차는 해당 경로로 이동할 수 없습니다.");
        }
        return create차Pattern(newPath, additionalSize);
    }

    private static List<Pattern> create차Pattern(차Path newPath, int additionalSize) {
        return Collections.nCopies(additionalSize, newPath.getPattern());
    }

    public Pattern getPattern() {
        return this.patterns.get(0);
    }
}
