package domain.pattern;

import static domain.pattern.Direction.DOWN;
import static domain.pattern.Direction.LEFT;
import static domain.pattern.Direction.LEFT_DOWN;
import static domain.pattern.Direction.LEFT_UP;
import static domain.pattern.Direction.RIGHT;
import static domain.pattern.Direction.RIGHT_DOWN;
import static domain.pattern.Direction.RIGHT_UP;
import static domain.pattern.Direction.UP;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneralPath extends Path {

    public GeneralPath() {
        super(List.of(RIGHT, DOWN, LEFT, UP, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN), createPatternMap());
    }

    private static Map<Direction, List<Pattern>> createPatternMap() {
        return Stream.of(
                Map.entry(RIGHT, List.of(Pattern.RIGHT)),
                Map.entry(DOWN, List.of(Pattern.DOWN)),
                Map.entry(LEFT, List.of(Pattern.LEFT)),
                Map.entry(UP, List.of(Pattern.UP)),
                Map.entry(RIGHT_UP, List.of(Pattern.DIAGONAL_UP_RIGHT)),
                Map.entry(RIGHT_DOWN, List.of(Pattern.DIAGONAL_DOWN_RIGHT)),
                Map.entry(LEFT_UP, List.of(Pattern.DIAGONAL_UP_LEFT)),
                Map.entry(LEFT_DOWN, List.of(Pattern.DIAGONAL_DOWN_LEFT))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
