package domain.pattern;

import static domain.pattern.Direction.DOWN;
import static domain.pattern.Direction.LEFT;
import static domain.pattern.Direction.RIGHT;
import static domain.pattern.Direction.UP;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChariotPath extends Path {

    public ChariotPath() {
        super(List.of(RIGHT, DOWN, LEFT, UP), createPatternMap());
    }

    private static Map<Direction, List<Pattern>> createPatternMap() {
        return Stream.of(
                Map.entry(RIGHT, List.of(Pattern.RIGHT)),
                Map.entry(DOWN, List.of(Pattern.DOWN)),
                Map.entry(LEFT, List.of(Pattern.LEFT)),
                Map.entry(UP, List.of(Pattern.UP))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
