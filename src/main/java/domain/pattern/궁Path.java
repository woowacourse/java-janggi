package domain.pattern;

import java.util.Arrays;
import java.util.List;

public enum 궁Path {
    RIGHT(List.of(Pattern.RIGHT)),
    DOWN(List.of(Pattern.DOWN)),
    LEFT(List.of(Pattern.LEFT)),
    UP(List.of(Pattern.UP));

    private final List<Pattern> patterns;

    궁Path(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    public static List<Pattern> getPath(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return Arrays.stream(궁Path.values())
                .filter(path -> {
                    List<Pattern> patterns = path.patterns;

                    int newRow = beforeRow + patterns.stream().mapToInt(Pattern::getX).sum();
                    int newColumn = beforeColumn + patterns.stream().mapToInt(Pattern::getY).sum();

                    return newRow == afterRow && newColumn == afterColumn;
                })
                .findFirst()
                .map(궁Path::getPatterns)
                .orElseThrow(() -> new IllegalStateException("궁은 해당 경로로 이동할 수 없습니다."));
    }

    public List<Pattern> getPatterns() {
        return this.patterns;
    }
}
