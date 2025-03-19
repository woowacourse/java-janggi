package domain.pattern;

import domain.Position;
import java.util.Arrays;
import java.util.List;

public enum 졸Path {
    RIGHT(List.of(Pattern.RIGHT)),
    LEFT(List.of(Pattern.LEFT)),
    UP(List.of(Pattern.UP));

    private final List<Pattern> patterns;

    졸Path(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    public static List<Pattern> getPath(Position beforePosition, Position afterPosition) {
        return Arrays.stream(졸Path.values())
                .filter(path -> {
                    List<Pattern> patterns = path.patterns;
                    Position newPosition = beforePosition.move(patterns);

                    return newPosition.equals(afterPosition);
                })
                .findFirst()
                .map(졸Path::getPatterns)
                .orElseThrow(() -> new IllegalStateException("졸은 해당 경로로 이동할 수 없습니다."));
    }

    public List<Pattern> getPatterns() {
        return this.patterns;
    }
}
