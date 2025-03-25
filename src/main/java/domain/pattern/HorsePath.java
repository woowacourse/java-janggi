package domain.pattern;

import static domain.pattern.Direction.DOWN_LEFT;
import static domain.pattern.Direction.DOWN_RIGHT;
import static domain.pattern.Direction.LEFT_DOWN;
import static domain.pattern.Direction.LEFT_UP;
import static domain.pattern.Direction.RIGHT_DOWN;
import static domain.pattern.Direction.RIGHT_UP;
import static domain.pattern.Direction.UP_LEFT;
import static domain.pattern.Direction.UP_RIGHT;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HorsePath extends Path {
    public HorsePath() {
        super(List.of(UP_RIGHT, RIGHT_UP, RIGHT_DOWN, DOWN_RIGHT, DOWN_LEFT, LEFT_DOWN, LEFT_UP, UP_LEFT),
                createPatternMap());
    }

    private static Map<Direction, List<Pattern>> createPatternMap() {
        return Stream.of(
                Map.entry(UP_RIGHT, List.of(Pattern.UP, Pattern.DIAGONAL_UP_RIGHT)),
                Map.entry(RIGHT_UP, List.of(Pattern.RIGHT, Pattern.DIAGONAL_UP_RIGHT)),
                Map.entry(RIGHT_DOWN, List.of(Pattern.RIGHT, Pattern.DIAGONAL_DOWN_RIGHT)),
                Map.entry(DOWN_RIGHT, List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_RIGHT)),
                Map.entry(DOWN_LEFT, List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_LEFT)),
                Map.entry(LEFT_DOWN, List.of(Pattern.LEFT, Pattern.DIAGONAL_DOWN_LEFT)),
                Map.entry(LEFT_UP, List.of(Pattern.LEFT, Pattern.DIAGONAL_UP_LEFT)),
                Map.entry(UP_LEFT, List.of(Pattern.UP, Pattern.DIAGONAL_UP_LEFT))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
