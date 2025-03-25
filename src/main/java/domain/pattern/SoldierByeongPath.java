package domain.pattern;

import static domain.pattern.Direction.DOWN;
import static domain.pattern.Direction.LEFT;
import static domain.pattern.Direction.RIGHT;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SoldierByeongPath extends Path {

    public SoldierByeongPath() {
        super(List.of(RIGHT, DOWN, LEFT), createPatternMap());
    }

    private static Map<Direction, List<Pattern>> createPatternMap() {
        return Stream.of(
                Map.entry(RIGHT, List.of(Pattern.RIGHT)),
                Map.entry(DOWN, List.of(Pattern.DOWN)),
                Map.entry(LEFT, List.of(Pattern.LEFT))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
