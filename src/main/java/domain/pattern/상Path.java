package domain.pattern;

import domain.Position;
import java.util.Arrays;
import java.util.List;

public enum 상Path {
    UP_RIGHT(List.of(Pattern.UP, Pattern.DIAGONAL_UP_RIGHT, Pattern.DIAGONAL_UP_RIGHT)),
    RIGHT_UP(List.of(Pattern.RIGHT, Pattern.DIAGONAL_UP_RIGHT, Pattern.DIAGONAL_UP_RIGHT)),
    RIGHT_DOWN(List.of(Pattern.RIGHT, Pattern.DIAGONAL_DOWN_RIGHT, Pattern.DIAGONAL_DOWN_RIGHT)),
    DOWN_RIGHT(List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_RIGHT, Pattern.DIAGONAL_DOWN_RIGHT)),
    DOWN_LEFT(List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_LEFT, Pattern.DIAGONAL_DOWN_LEFT)),
    LEFT_DOWN(List.of(Pattern.LEFT, Pattern.DIAGONAL_DOWN_LEFT, Pattern.DIAGONAL_DOWN_LEFT)),
    LEFT_UP(List.of(Pattern.LEFT, Pattern.DIAGONAL_UP_LEFT, Pattern.DIAGONAL_UP_LEFT)),
    UP_LEFT(List.of(Pattern.UP, Pattern.DIAGONAL_UP_LEFT, Pattern.DIAGONAL_UP_LEFT));

    private List<Pattern> patterns;

    상Path(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    public static List<Pattern> getPath(Position beforePosition, Position afterPosition) {
        return Arrays.stream(상Path.values())
                .filter(path -> {
                    List<Pattern> patterns = path.patterns;
                    Position newPosition = beforePosition.move(patterns);

                    return newPosition.equals(afterPosition);
                })
                .findFirst()
                .map(상Path::getPatterns)
                .orElseThrow(() -> new IllegalStateException("상은 해당 경로로 이동할 수 없습니다."));
    }

    public List<Pattern> getPatterns() {
        return this.patterns;
    }
}
