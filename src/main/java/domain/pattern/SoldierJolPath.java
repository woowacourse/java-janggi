package domain.pattern;

import static domain.pattern.Direction.LEFT;
import static domain.pattern.Direction.RIGHT;
import static domain.pattern.Direction.UP;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SoldierJolPath extends Path {

    public SoldierJolPath() {
        super(List.of(RIGHT, LEFT, UP), createPatternMap());
    }

    private static Map<Direction, List<Pattern>> createPatternMap() {
        return Stream.of(
                Map.entry(RIGHT, List.of(Pattern.RIGHT)),
                Map.entry(LEFT, List.of(Pattern.LEFT)),
                Map.entry(UP, List.of(Pattern.UP))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
