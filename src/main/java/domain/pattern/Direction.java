package domain.pattern;

import java.util.List;
import java.util.Map;

public enum Direction {
    RIGHT,
    DOWN,
    LEFT,
    UP,
    UP_RIGHT,
    RIGHT_UP,
    RIGHT_DOWN,
    DOWN_RIGHT,
    DOWN_LEFT,
    LEFT_DOWN,
    LEFT_UP,
    UP_LEFT;

    public static Map<Direction, List<Pattern>> createElephantPatternMap() {
        return Map.ofEntries(
                Map.entry(UP_RIGHT, List.of(Pattern.UP, Pattern.DIAGONAL_UP_RIGHT, Pattern.DIAGONAL_UP_RIGHT)),
                Map.entry(RIGHT_UP, List.of(Pattern.RIGHT, Pattern.DIAGONAL_UP_RIGHT, Pattern.DIAGONAL_UP_RIGHT)),
                Map.entry(RIGHT_DOWN, List.of(Pattern.RIGHT, Pattern.DIAGONAL_DOWN_RIGHT, Pattern.DIAGONAL_DOWN_RIGHT)),
                Map.entry(DOWN_RIGHT, List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_RIGHT, Pattern.DIAGONAL_DOWN_RIGHT)),
                Map.entry(DOWN_LEFT, List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_LEFT, Pattern.DIAGONAL_DOWN_LEFT)),
                Map.entry(LEFT_DOWN, List.of(Pattern.LEFT, Pattern.DIAGONAL_DOWN_LEFT, Pattern.DIAGONAL_DOWN_LEFT)),
                Map.entry(LEFT_UP, List.of(Pattern.LEFT, Pattern.DIAGONAL_UP_LEFT, Pattern.DIAGONAL_UP_LEFT)),
                Map.entry(UP_LEFT, List.of(Pattern.UP, Pattern.DIAGONAL_UP_LEFT, Pattern.DIAGONAL_UP_LEFT))
        );
    }

    public static Map<Direction, List<Pattern>> createGeneralOrGuardPatternMap() {
        return Map.ofEntries(
                Map.entry(RIGHT, List.of(Pattern.RIGHT)),
                Map.entry(DOWN, List.of(Pattern.DOWN)),
                Map.entry(LEFT, List.of(Pattern.LEFT)),
                Map.entry(UP, List.of(Pattern.UP)),
                Map.entry(RIGHT_UP, List.of(Pattern.DIAGONAL_UP_RIGHT)),
                Map.entry(RIGHT_DOWN, List.of(Pattern.DIAGONAL_DOWN_RIGHT)),
                Map.entry(LEFT_UP, List.of(Pattern.DIAGONAL_UP_LEFT)),
                Map.entry(LEFT_DOWN, List.of(Pattern.DIAGONAL_DOWN_LEFT))
        );
    }

    public static Map<Direction, List<Pattern>> createHorsePatternMap() {
        return Map.ofEntries(
                Map.entry(UP_RIGHT, List.of(Pattern.UP, Pattern.DIAGONAL_UP_RIGHT)),
                Map.entry(RIGHT_UP, List.of(Pattern.RIGHT, Pattern.DIAGONAL_UP_RIGHT)),
                Map.entry(RIGHT_DOWN, List.of(Pattern.RIGHT, Pattern.DIAGONAL_DOWN_RIGHT)),
                Map.entry(DOWN_RIGHT, List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_RIGHT)),
                Map.entry(DOWN_LEFT, List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_LEFT)),
                Map.entry(LEFT_DOWN, List.of(Pattern.LEFT, Pattern.DIAGONAL_DOWN_LEFT)),
                Map.entry(LEFT_UP, List.of(Pattern.LEFT, Pattern.DIAGONAL_UP_LEFT)),
                Map.entry(UP_LEFT, List.of(Pattern.UP, Pattern.DIAGONAL_UP_LEFT))
        );
    }

    public static Map<Direction, List<Pattern>> createChariotOrCannonPatternMap() {
        return Map.ofEntries(
                Map.entry(RIGHT, List.of(Pattern.RIGHT)),
                Map.entry(DOWN, List.of(Pattern.DOWN)),
                Map.entry(LEFT, List.of(Pattern.LEFT)),
                Map.entry(UP, List.of(Pattern.UP))
        );
    }

    public static Map<Direction, List<Pattern>> createByeongPatternMap() {
        return Map.ofEntries(
                Map.entry(RIGHT, List.of(Pattern.RIGHT)),
                Map.entry(DOWN, List.of(Pattern.DOWN)),
                Map.entry(LEFT, List.of(Pattern.LEFT))
        );
    }

    public static Map<Direction, List<Pattern>> createJolPatternMap() {
        return Map.ofEntries(
                Map.entry(RIGHT, List.of(Pattern.RIGHT)),
                Map.entry(LEFT, List.of(Pattern.LEFT)),
                Map.entry(UP, List.of(Pattern.UP))
        );
    }
}
