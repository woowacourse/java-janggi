package domain.pattern;

import java.util.Arrays;
import java.util.List;

public enum 마Path {
    UP_RIGHT(List.of(Pattern.UP, Pattern.DIAGONAL_UP_RIGHT)),
    RIGHT_UP(List.of(Pattern.RIGHT, Pattern.DIAGONAL_UP_RIGHT)),
    RIGHT_DOWN(List.of(Pattern.RIGHT, Pattern.DIAGONAL_DOWN_RIGHT)),
    DOWN_RIGHT(List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_RIGHT)),
    DOWN_LEFT(List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_LEFT)),
    LEFT_DOWN(List.of(Pattern.LEFT, Pattern.DIAGONAL_DOWN_LEFT)),
    LEFT_UP(List.of(Pattern.LEFT, Pattern.DIAGONAL_UP_LEFT)),
    UP_LEFT(List.of(Pattern.UP, Pattern.DIAGONAL_UP_LEFT));

    private List<Pattern> patterns;

    마Path(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    public static List<Pattern> getPath(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return Arrays.stream(마Path.values())
                .filter(path -> {
                    List<Pattern> patterns = path.patterns;

                    int newRow = beforeRow + patterns.stream().mapToInt(Pattern::getX).sum();
                    int newColumn = beforeColumn + patterns.stream().mapToInt(Pattern::getY).sum();

                    return newRow == afterRow && newColumn == afterColumn;
                })
                .findFirst()
                .map(마Path::getPatterns)
                .orElseThrow(() -> new IllegalStateException("마는 해당 경로로 이동할 수 없습니다."));
    }

    public List<Pattern> getPatterns() {
        return this.patterns;
    }
}
